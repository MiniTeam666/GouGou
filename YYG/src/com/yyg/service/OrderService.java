package com.yyg.service;

import com.j256.ormlite.dao.Dao;
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
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by line on 2016/9/6.
 */
public class OrderService extends Observable implements Service {

	private Dao<OrderGroup,String> orderGroupDao;

	private Dao<Order,String> orderDao;

	private ConcurrentHashMap<Integer,OrderTimeoutRunnable> mOrderTimeoutMap;

	private ProductService productService;

	public OrderService(){
		orderDao = DatabaseManager.getInstance().createDao(Order.class);
		orderGroupDao = DatabaseManager.getInstance().createDao(OrderGroup.class);
		productService = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
		mOrderTimeoutMap = new ConcurrentHashMap<Integer, OrderTimeoutRunnable>();
	}

	public OrderResult createOrderGroup(User user, HashMap<Integer,Integer> orders){

		OrderResult result = new OrderResult();
		try{

			//TODO　事务开发
			final OrderGroup orderGroup = new OrderGroup();
			orderGroup.price = 0;
			orderGroup.createTime = System.currentTimeMillis();
			orderGroup.statu = Order.OrderStatu.waitpay.getStatus();
			orderGroup.user = user;
			orderGroup.orders = new ArrayList<>();
//			if(orderGroupDao.create(orderGroup) == 1){
//				return 0;
//			}

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


			if(hasNoStockLotteries.size() == 0 && orderGroupDao.create(orderGroup) == 1){

				//TODO 生成支付接口,超时时间要大于支付接口超时时间
				LogManager.getLogger().error("create orderGroup successful ! id : " + orderGroup.id + " time : "  + YYGUtils.getTimeStr(orderGroup.createTime));
				OrderTimeoutRunnable runnable = new OrderTimeoutRunnable(orderGroup, AppConstant.ORDER_PAY_TIMEOUT,this);
				mOrderTimeoutMap.put(orderGroup.id,runnable);
				ThreadManager.executeOnTimeoutThread(runnable,AppConstant.ORDER_PAY_TIMEOUT);

				result.success = true ;


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
				if(hasNoStockLotteries.size() > 0 ){
					JSONArray jsonArray = new JSONArray();
					for(int i = 0 ; i < hasNoStockLotteries.size() ; i ++ ){
						JSONObject item = new JSONObject();
						Lottery lottery = hasNoStockLotteries.get(i);
						item.put("id",hasNoStockLotteries.get(i).id);
						item.put("stock",lottery.remainCountOfQulification);
						jsonArray.put(item);
					}

					result.errCode = OrderResult.OrderResultCode.CreateOrder.CREATE_FAIL;
					result.array = jsonArray;
				}else{
					result.errCode = OrderResult.OrderResultCode.CreateOrder.NO_STOCK;
				}

			}

		}catch (Exception e){
			e.printStackTrace();
			result.success = false;
			result.errCode = OrderResult.OrderResultCode.CreateOrder.Exception;
			result.errMsg = e.toString();
		}

		return result;
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

//	public Order createOrder(User user,int lotteryID,int count,OrderGroup orderGroup){
//		final Order order;
//		try{
//			Lottery lottery = productService.getLottery(lotteryID);
//			int stockStatus = lottery.lotteryBuyController.decrementStock(count);
//			if(stockStatus != 0) {
//				LogManager.getLogger().warn("create order fail , lottery stock not permit " + stockStatus);
//				return null;
//			}
//
//			order = new Order();
//			order.user = user;
//			order.time = System.currentTimeMillis();
//			order.state = Order.OrderStatu.waitpay.getStatus();
//			order.joinTime = count;
//			order.lottery = lottery;
//			order.orderGroup = orderGroup;
//			boolean createOrder = orderDao.create(order) == 1;
//			if(createOrder){
//				//TODO 生成支付接口,超时时间要大于支付接口超时时间
//				LogManager.getLogger().error("create order successful ! id : " + order.id + " time : "  + YYGUtils.getTimeStr(order.time));
//				OrderTimeoutRunnable runnable = new OrderTimeoutRunnable(order, AppConstant.ORDER_PAY_TIMEOUT,this);
//				mOrderTimeoutMap.put(order.id,runnable);
//				ThreadManager.executeOnTimeoutThread(runnable,AppConstant.ORDER_PAY_TIMEOUT);
//			}else{
//				LogManager.getLogger().error("create order fail ! user : " + user.id + ", buyCount : " + count);
//				ThreadManager.executeOnNormalThread(new Runnable() {
//					@Override
//					public void run() {
//						notifyOrderPayResult(order, Message.ERROR_CODE_ORDERE_CREATE_FAIL);
//					}
//				});
//			}
//
//		}catch (SQLException e){
//			e.printStackTrace();
//		}
//		return null;
//	}

	public void notifyOrderPayResult(OrderGroup orderGroup,int result){
		int id = orderGroup.id;
		OrderTimeoutRunnable runnable = mOrderTimeoutMap.remove(id);
		if(runnable != null){
			boolean cancelRet = runnable.cancel();
			if(!cancelRet && result == 0){
				//TODO 回滚timeout操作
			}
		}

		Iterator<Order> orderIterator = orderGroup.orders.iterator();
		while (orderIterator.hasNext()){
			Order order = orderIterator.next();
			Message msg = Message.getUpdateStockMsg(order.lottery.id, result, order);
			notifyObservers(msg);
			setChanged();
		}
	}



}
