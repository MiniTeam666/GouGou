package com.yyg.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yyg.*;
import com.yyg.model.Lottery;
import com.yyg.model.Order;
import com.yyg.model.OrderGroup;
import com.yyg.model.User;
import com.yyg.model.vo.OrderGroupPayResult;
import com.yyg.model.vo.OrderVo;
import com.yyg.utils.Message;
import com.yyg.utils.OrderTimeoutRunnable;
import com.yyg.utils.YYGUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by line on 2016/9/6.
 */
public class OrderService extends Observable implements Service {

	private Dao<OrderGroup,Integer> orderGroupDao;

	private Dao<Order,Integer> orderDao;

//	private ConcurrentHashMap<Integer,OrderTimeoutRunnable> mOrderTimeoutMap;

	private ProductService productService;

	private ConcurrentHashMap<Integer,OrderGroupPayResult> mPayingOrderGroups;

	public OrderService(){
		orderDao = DatabaseManager.getInstance().createDao(Order.class);
		orderGroupDao = DatabaseManager.getInstance().createDao(OrderGroup.class);
//		mOrderTimeoutMap = new ConcurrentHashMap<>();
		mPayingOrderGroups = new ConcurrentHashMap<>();

		productService = (ProductService) ServiceManager.getService(ServiceManager.Product_Service);
	}

	@Override
	public void doInInit(){

	}

	public OrderGroupPayResult createOrderGroup(User user, HashMap<Integer,Integer> params){

		OrderGroupPayResult result;
		try{

			List<Lottery> hasNoStockLotteries = new ArrayList<>();
            List<Order> orders = new ArrayList<>();
            int totalPrice = 0;
			for(Integer lotteryID : params.keySet()){

                int count = params.get(lotteryID);

                Lottery lottery = productService.getLottery(lotteryID);
                int stockStatus = lottery.lotteryBuyController.decrementStock(count);
                if(stockStatus != 0) {
                    YYGUtils.log(Level.ERROR,"create order fail , lottery stock not permit . lotteryID : "
                            + lotteryID + ", errCode : " + stockStatus);
                    hasNoStockLotteries.add(lottery);
                    break;
                }

				Order order = new Order();
				order.user = user;
				order.time = System.currentTimeMillis();
				order.state = Order.OrderStatu.waitpay.getStatus();
				order.joinTime = count;
				order.lottery = lottery;
                orders.add(order);

                totalPrice += order.joinTime;
			}


            result = new OrderGroupPayResult(null);


			if(hasNoStockLotteries.size() == 0 && orders.size() > 0){

                final OrderGroup orderGroup = new OrderGroup();
                orderGroup.price = totalPrice;
                orderGroup.createTime = System.currentTimeMillis();
                orderGroup.statu = Order.OrderStatu.waitpay.getStatus();
                orderGroup.user = user;
                orderGroup.orders = orderGroupDao.getEmptyForeignCollection("orders");
                if(orderGroupDao.create(orderGroup) != 1){
                    YYGUtils.log(Level.ERROR,"create order group fail ! ");
                    return null;
                }

                result.orderGroup = orderGroup;

                //持久化具体订单
                for(int i = 0 ; i < orders.size(); i ++ ){
                    Order order = orders.get(i);
                    order.orderGroup = orderGroup;
                    orderGroup.orders.add(order);
                }


				String payLink = ThirdPay.getInstance().createWxPayUrl(orderGroup);
				if(YYGUtils.isEmptyText(payLink)){
					result.success = false;
					result.errCode = OrderGroupPayResult.OrderResultCode.CreateOrder.CREAT_PAY_LINK_ERROR;
					result.errMsg = "create pay link error !";
				}else {

					YYGUtils.log(Level.INFO,"create orderGroup successful ! id : " + orderGroup.id + " time : " + YYGUtils.getTimeStr(orderGroup.createTime));

//					OrderTimeoutRunnable runnable = new OrderTimeoutRunnable(orderGroup, AppConstant.ORDER_PAY_TIMEOUT, this);
//					mOrderTimeoutMap.put(orderGroup.id, runnable);
//					ThreadManager.executeOnTimeoutThread(runnable, AppConstant.ORDER_PAY_TIMEOUT);
//				ThreadManager.executeOnTimeoutThread(new Runnable() {
//					@Override
//					public void run() {
//						System.out.println("test thread manaer!");
//					}
//				}, AppConstant.ORDER_PAY_TIMEOUT);

					result.success = true;
					result.payLink = payLink;

					addOrderGroup2PayingList(orderGroup,result);
				}
			}else{

				result.success = false ;
//
//				LogManager.getLogger().error("create order fail ! user : " + user.id + ", buyCount : " + params.toString());
////				ThreadManager.executeOnNormalThread(new Runnable() {
////					@Override
////					public void run() {
////						notifyOrderPayResult(orderGroup, Message.ERROR_CODE_ORDERE_CREATE_FAIL);
////					}
////				});

				//返回错误列表
				HashMap<Integer,Integer> map = new HashMap<>();
				for(int i = 0 ; i < hasNoStockLotteries.size() ; i ++ ){
					Lottery lottery = hasNoStockLotteries.get(i);
					int id = lottery.id;
					int stock = lottery.remainCountOfQulification;
					map.put(id,stock);
				}

				result.errCode = OrderGroupPayResult.OrderResultCode.CreateOrder.CREATE_FAIL;
				result.errMsg = "has some product stock not enough buy ! ";
				result.errList = map;

				for(int i = 0 ; i < orders.size(); i ++ ){
					Order order = orders.get(i);
					Message msg = Message.getUpdateStockMsg(order.lottery.id,Message.ERROR_CODE_ORDERE_CREATE_FAIL,order);
					setChanged();
					notifyObservers(msg);
				}

			}

		}catch (Exception e){
			e.printStackTrace();
            result = new OrderGroupPayResult(new OrderGroup());
			result.success = false;
			result.errCode = OrderGroupPayResult.OrderResultCode.CreateOrder.Exception;
			result.errMsg = e.toString();
		}

		return result;
	}

	public List<Order> getLatestSuccessOrder(int lotteryID, int cnt){
		try{

			List<Order> orderList = orderDao.queryBuilder().where()
					.eq("lottery_id",lotteryID)
					.and()
					.eq(Order.FIELD_STATUS, Order.OrderStatu.paySuccess.getStatus())
					.query();

			if(orderList != null && !orderList.isEmpty()){

				//按照购买时间逆序排序
				Collections.sort(orderList, new Comparator<Order>() {
					@Override
					public int compare(Order o1, Order o2) {
						return o1.time > o2.time ? -1 : 1;
					}
				});

				cnt = orderList.size() > cnt ? cnt : orderList.size();
				return orderList.subList(0,cnt);
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}

	public Order getLuckyOrder(int lotteryID,int luckyNum){
		List<Order> orderList = getLatestSuccessOrder(lotteryID,Integer.MAX_VALUE);
		if(orderList != null && !orderList.isEmpty()){
			int n = orderList.size();
			for(int i = 0 ; i < n ; i ++){
				Order order = orderList.get(i);
				if(YYGUtils.hasLuckyNum(YYGUtils.byte2String(order.luckNums),luckyNum)){
					return order;
				}
			}
		}
		return null;
	}

	public List<OrderVo> getOrders(int lotteryID, int startRow, int count){
		try{
			List<Order> orderList = CacheManager.getInstance().getOrders(lotteryID);
			if(orderList == null){
				orderList = orderDao.queryBuilder().where().eq("lottery_id",lotteryID).query();
				CacheManager.getInstance().cacheOrders(lotteryID,orderList);
			}

			if(orderList != null){
				int end = startRow + count < orderList.size() ? startRow + count : orderList.size();
				orderList = orderList.subList(startRow,end);

				//sort
				Collections.sort(orderList, new Comparator<Order>() {
					@Override
					public int compare(Order o1, Order o2) {
						return o1.time > o2.time ? -1 : 1;
					}
				});

				return OrderVo.translateOrders(orderList);
			}

		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateOrder(Order order){
		try{
			int num = orderDao.update(order);
			if(num == 1) {
				CacheManager.getInstance().cacheOrder(order);
				return true;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateOrderGroup(OrderGroup group){
		try{
			int line = orderGroupDao.update(group);
			if(line == 1){
				return true;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	private void addOrderGroup2PayingList(OrderGroup orderGroup,OrderGroupPayResult result){
		mPayingOrderGroups.put(orderGroup.id,result);
		if(mPayingOrderGroups.size() > 10000){
			OrderGroupPayResult older = null;
            int removeCount = 0;
			for(Integer key : mPayingOrderGroups.keySet()){
				OrderGroupPayResult item = mPayingOrderGroups.get(key);
                if(item.orderGroup.statu != Order.OrderStatu.waitpay.getStatus()) {
                    mPayingOrderGroups.remove(item);
                    removeCount++;
                }else if(System.currentTimeMillis() - item.orderGroup.createTime > 12 * 3600 * 1000){
                    //超过36h 没支付的,不在内存保存状态
                    mPayingOrderGroups.remove(item);
                    removeCount++;
                }
			}
			LogManager.getLogger().info("paying order list is full ! remove out of wait-pay order , cnt : " + removeCount);
		}
	}

	public void handleOrderGroupPayResult(int orderGroupID,int result){
		OrderGroupPayResult orderGroupPayResult = mPayingOrderGroups.get(orderGroupID);
		OrderGroup orderGroup = null ;
		if(orderGroupPayResult == null){
			try {
				orderGroup = (OrderGroup) orderGroupDao.queryForId(orderGroupID);
			}catch (SQLException e){
				e.printStackTrace();
			}
		}else{
			orderGroup = orderGroupPayResult.orderGroup;
		}

		if(orderGroup == null)
		    return;

        if(orderGroup.statu != Order.OrderStatu.waitpay.getStatus()) {
            LogManager.getLogger().error("duplicate pay result for " + orderGroupID + " , result : " + result);
            return;
        }

        if(result == 0){
            orderGroup.statu = Order.OrderStatu.paySuccess.getStatus();
        }else {
            orderGroup.statu = Order.OrderStatu.payFail.getStatus();
        }

        synchronized (orderGroup) {
			notifyOrderPayResult(orderGroup, result);
		}

		// 通知异步等待结果的页面
        if(orderGroupPayResult != null)
            orderGroupPayResult.notifyStatusChange();
	}

	public void notifyOrderPayResult(OrderGroup orderGroup,int result){

//		int id = orderGroup.id;

//		OrderTimeoutRunnable runnable = mOrderTimeoutMap.remove(id);
//		if(runnable != null && result != Message.ERROR_CODE_ORDERE_PAY_TIMEOUT){
//			boolean cancelRet = runnable.cancel();
//			if(!cancelRet && result == 0){
//				//TODO 回滚timeout操作
//			}
//		}

		updateOrderGroup(orderGroup);

		Iterator<Order> orderIterator = orderGroup.orders.iterator();
		while (orderIterator.hasNext()){
			Order order = orderIterator.next();
			LogManager.getLogger().error("update order status ! id : " + order.id);
			Message msg = Message.getUpdateStockMsg(order.lottery.id, result, order);
			setChanged();
			notifyObservers(msg);
		}

	}

	public JSONArray getUserLuckyNums(int userID,int lotteryID){
		JSONArray data = new JSONArray();
		try{
			QueryBuilder<Order,Integer> orderBuilder = orderDao.queryBuilder();
			orderBuilder.where()
					.eq("user_id",userID)
					.and()
					.eq("state", Order.OrderStatu.paySuccess.getStatus())
					.and()
					.eq("lottery_id",lotteryID);

			List<Order> orders = orderBuilder.query();
			if(orders != null){
				int n = orders.size();
				for(int i = 0 ; i < n ; i ++ ){
					Order order = orders.get(i);
					String luckNums = YYGUtils.byte2String(order.luckNums);
					if(YYGUtils.isEmptyText(luckNums))
						continue;
					String[] luckNumArray = luckNums.split(AppConstant.PRODUCT_LUCKNUM_SPLIT_CHAR);
					for(String luckNum : luckNumArray){
						data.put(luckNum);
					}
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return data;
	}

	public OrderGroupPayResult getOrderPayResult(int orderGroupID){
	    OrderGroupPayResult result = mPayingOrderGroups.get(orderGroupID);
        if(result == null){
            try {

                OrderGroup orderGroup = orderGroupDao.queryForId(orderGroupID);
                if(orderGroup == null)
                    return null;

				result = new OrderGroupPayResult(orderGroup);
                if(orderGroup.statu == Order.OrderStatu.waitpay.getStatus()) {
                    addOrderGroup2PayingList(orderGroup, result);
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

		return result;
	}

}
