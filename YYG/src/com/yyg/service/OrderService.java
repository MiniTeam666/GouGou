package com.yyg.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yyg.*;
import com.yyg.model.Lottery;
import com.yyg.model.Order;
import com.yyg.model.OrderGroup;
import com.yyg.model.User;
import com.yyg.model.vo.OrderResult;
import com.yyg.model.vo.OrderVo;
import com.yyg.utils.Message;
import com.yyg.utils.OrderTimeoutRunnable;
import com.yyg.utils.YYGUtils;
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

	private ConcurrentHashMap<Integer,OrderTimeoutRunnable> mOrderTimeoutMap;

	private ProductService productService;

	private ConcurrentHashMap<Integer,OrderGroup> mPayingOrderGroups;

	public OrderService(){
		orderDao = DatabaseManager.getInstance().createDao(Order.class);
		orderGroupDao = DatabaseManager.getInstance().createDao(OrderGroup.class);
		productService = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
		mOrderTimeoutMap = new ConcurrentHashMap<Integer, OrderTimeoutRunnable>();
		mPayingOrderGroups = new ConcurrentHashMap<>();
	}

	public OrderResult createOrderGroup(User user, HashMap<Integer,Integer> orders){

		OrderResult result = new OrderResult();
		try{

			final OrderGroup orderGroup = new OrderGroup();
			orderGroup.price = 0;
			orderGroup.createTime = System.currentTimeMillis();
			orderGroup.statu = Order.OrderStatu.waitpay.getStatus();
			orderGroup.user = user;
			orderGroup.orders = orderGroupDao.getEmptyForeignCollection("orders");
			if(orderGroupDao.create(orderGroup) != 1){
				LogManager.getLogger().error("create order group fail ! ");
				return null;
			}

			List<Lottery> hasNoStockLotteries = new ArrayList<>();
			for(Integer lotteryID : orders.keySet()){
				int count = orders.get(lotteryID);
				Order order = new Order();
				order.user = user;
				order.time = System.currentTimeMillis();
				order.state = Order.OrderStatu.waitpay.getStatus();
				order.joinTime = count;
				order.lottery = productService.getLottery(lotteryID);
				order.orderGroup = orderGroup;

				Lottery lottery = productService.getLottery(lotteryID);
				int stockStatus = lottery.lotteryBuyController.decrementStock(count);
				if(stockStatus != 0) {
					LogManager.getLogger().warn("create order fail , lottery stock not permit . lotteryID : "
							+ lotteryID + ", errCode : " + stockStatus);
					hasNoStockLotteries.add(lottery);
					continue;
				}

				orderGroup.orders.add(order);
				orderGroup.price += order.joinTime;

			}


			if(hasNoStockLotteries.size() == 0){

				String payLink = ThirdPay.getInstance().createWxPayUrl(orderGroup);
				if(YYGUtils.isEmptyText(payLink)){
					result.success = false;
					result.errCode = OrderResult.OrderResultCode.CreateOrder.CREAT_PAY_LINK_ERROR;
					result.errMsg = "create pay link error !";
				}else {

					LogManager.getLogger().info("create orderGroup successful ! id : " + orderGroup.id + " time : " + YYGUtils.getTimeStr(orderGroup.createTime));

//					OrderTimeoutRunnable runnable = new OrderTimeoutRunnable(orderGroup, AppConstant.ORDER_PAY_TIMEOUT, this);
//					mOrderTimeoutMap.put(orderGroup.id, runnable);
//					ThreadManager.executeOnTimeoutThread(runnable, AppConstant.ORDER_PAY_TIMEOUT);
//				ThreadManager.executeOnTimeoutThread(new Runnable() {
//					@Override
//					public void run() {
//						System.out.println("test thread manaer!");
//					}
//				}, AppConstant.ORDER_PAY_TIMEOUT);

					addOrderGroup2PayingList(orderGroup);
					result.success = true;
					result.payLink = payLink;
					result.orderID = orderGroup.id;
				}
			}else{

				result.success = false ;

				LogManager.getLogger().error("create order fail ! user : " + user.id + ", buyCount : " + orders.toString());
				ThreadManager.executeOnNormalThread(new Runnable() {
					@Override
					public void run() {
						notifyOrderPayResult(orderGroup, Message.ERROR_CODE_ORDERE_CREATE_FAIL);
					}
				});

				//返回错误列表
				HashMap<Integer,Integer> map = new HashMap<>();
				for(int i = 0 ; i < hasNoStockLotteries.size() ; i ++ ){
					Lottery lottery = hasNoStockLotteries.get(i);
					int id = lottery.id;
					int stock = lottery.remainCountOfQulification;
					map.put(id,stock);
				}

				result.errCode = OrderResult.OrderResultCode.CreateOrder.CREATE_FAIL;
				result.errMsg = "has some product stock not enough buy ! ";
				result.errList = map;
			}

		}catch (Exception e){
			e.printStackTrace();
			result.success = false;
			result.errCode = OrderResult.OrderResultCode.CreateOrder.Exception;
			result.errMsg = e.toString();
		}

		return result;
	}

	public List<Order> getLatestOrder(int lotteryID,int cnt){
		try{

			List<Order> orderList = orderDao.queryBuilder().where().eq("lottery_id",lotteryID).query();

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
		try{
			List<Order> orderList = orderDao.queryBuilder().where().eq("lottery_id",lotteryID).query();
			if(orderList != null && !orderList.isEmpty()){
				int n = orderList.size();
				for(int i = 0 ; i < n ; i ++){
					Order order = orderList.get(i);
					if(YYGUtils.hasLuckyNum(new String(order.luckNums),luckyNum)){
						return order;
					}
				}
			}

		}catch (SQLException e){
			e.printStackTrace();
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

	private void addOrderGroup2PayingList(OrderGroup orderGroup){
		mPayingOrderGroups.put(orderGroup.id,orderGroup);
		if(mPayingOrderGroups.size() < 10000){
			OrderGroup older = null;
			for(Integer key : mPayingOrderGroups.keySet()){
				OrderGroup item = mPayingOrderGroups.get(key);
				if(older == null || item.createTime < older.createTime){
					older = item;
				}
			}
			mPayingOrderGroups.remove(older);
		}
	}

	public void handleOrderGroupPayResult(int orderGroupID,int result){
		OrderGroup orderGroup = mPayingOrderGroups.get(orderGroupID);

		if(orderGroup == null){
			try {
				orderGroup = (OrderGroup) orderGroupDao.queryForId(orderGroupID);
			}catch (SQLException e){
				e.printStackTrace();
			}
		}

		if(orderGroup == null || orderGroup.statu != Order.OrderStatu.waitpay.getStatus()) {
			LogManager.getLogger().error("duplicate pay result for " + orderGroupID + " , result : " + result);
			return;
		}

		synchronized (orderGroup) {
			notifyOrderPayResult(orderGroup, result);
		}
	}

	public void notifyOrderPayResult(OrderGroup orderGroup,int result){
		int id = orderGroup.id;
		OrderTimeoutRunnable runnable = mOrderTimeoutMap.remove(id);
		if(runnable != null && result != Message.ERROR_CODE_ORDERE_PAY_TIMEOUT){
			boolean cancelRet = runnable.cancel();
			if(!cancelRet && result == 0){
				//TODO 回滚timeout操作
			}
		}

		if(result == 0){
			orderGroup.statu = Order.OrderStatu.paySuccess.getStatus();
		}else {
			orderGroup.statu = Order.OrderStatu.payFail.getStatus();
		}
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
			QueryBuilder<OrderGroup,Integer> groupBuilder = orderGroupDao.queryBuilder();
			groupBuilder.where().eq("lottery_id",lotteryID);
			QueryBuilder<Order,Integer> orderBuilder = orderDao.queryBuilder();
			orderBuilder.where().eq("user_id",userID).eq("status", Order.OrderStatu.paySuccess.getStatus());

			List<Order> orders = orderBuilder.join(groupBuilder).query();
			if(orders != null){
				int n = orders.size();
				for(int i = 0 ; i < n ; i ++ ){
					Order order = orders.get(i);
					String luckNums = new String(order.luckNums);
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

}
