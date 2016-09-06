package com.yyg.service;

import com.j256.ormlite.dao.Dao;
import com.yyg.AppConstant;
import com.yyg.DatabaseManager;
import com.yyg.ServiceManager;
import com.yyg.ThreadManager;
import com.yyg.model.Lottery;
import com.yyg.model.Order;
import com.yyg.model.OrderGroup;
import com.yyg.model.User;
import com.yyg.utils.Message;
import com.yyg.utils.OrderTimeoutRunnable;
import com.yyg.utils.YYGUtils;
import org.apache.logging.log4j.LogManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Observable;
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

	public int createOrderGroup(User user, int price, HashMap<Integer,Integer> orders){
		try{

			//TODO　事务开发
			OrderGroup orderGroup = new OrderGroup();
			orderGroup.price = price;
			orderGroup.createTime = System.currentTimeMillis();
			orderGroup.statu = Order.OrderStatu.waitpay.getStatus();
			orderGroup.user = user;

			if(orderGroupDao.create(orderGroup) == 1){
				return 0;
			}

			for(Integer lotteryID : orders.keySet()){
				int count = orders.get(lotteryID);
				Order order = createOrder(user,lotteryID,count,orderGroup);
				if(order == null){
					return lotteryID;
				}
			}

			return 0;

		}catch (SQLException e){
			e.printStackTrace();
		}
		return -1;
	}

	public Order createOrder(User user,int lotteryID,int count,OrderGroup orderGroup){
		final Order order;
		try{
			Lottery lottery = productService.getLottery(lotteryID);
			int stockStatus = lottery.lotteryInfo.decrementStock(count);
			if(stockStatus != 0) {
				LogManager.getLogger().warn("create order fail , lottery stock not permit " + stockStatus);
				return null;
			}

			order = new Order();
			order.user = user;
			order.time = System.currentTimeMillis();
			order.state = Order.OrderStatu.waitpay.getStatus();
			order.joinTime = count;
			order.lottery = lottery;
			order.orderGroup = orderGroup;
			boolean createOrder = orderDao.create(order) == 1;
			if(createOrder){
				//TODO 生成支付接口,超时时间要大于支付接口超时时间
				LogManager.getLogger().error("create order successful ! id : " + order.id + " time : "  + YYGUtils.getTimeStr(order.time));
				OrderTimeoutRunnable runnable = new OrderTimeoutRunnable(order, AppConstant.ORDER_PAY_TIMEOUT,this);
				mOrderTimeoutMap.put(order.id,runnable);
				ThreadManager.executeOnTimeoutThread(runnable,AppConstant.ORDER_PAY_TIMEOUT);
			}else{
				LogManager.getLogger().error("create order fail ! user : " + user.id + ", buyCount : " + count);
				ThreadManager.executeOnNormalThread(new Runnable() {
					@Override
					public void run() {
						notifyOrderPayResult(order, Message.ERROR_CODE_ORDERE_CREATE_FAIL);
					}
				});
			}

		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateOrder(Order order){
		try{

			if(orderDao.update(order) == 1)
				return true;

		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public void notifyOrderPayResult(Order order,int result){
		int id = order.id;
		OrderTimeoutRunnable runnable = mOrderTimeoutMap.remove(id);
		if(runnable != null){
			boolean cancelRet = runnable.cancel();
			if(!cancelRet && result == 0){
				//TODO 回滚timeout操作
			}
		}
		Message msg = Message.getUpdateStockMsg(order.lottery.id,result,order);
		notifyObservers(msg);
		setChanged();
	}

}
