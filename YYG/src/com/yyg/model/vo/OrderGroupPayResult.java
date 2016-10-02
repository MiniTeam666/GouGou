package com.yyg.model.vo;

import com.yyg.model.Order;
import com.yyg.model.OrderGroup;

import javax.servlet.AsyncContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by line on 2016/9/15.
 */
public class OrderGroupPayResult {

	public OrderGroup orderGroup;

	public boolean success;

	public int errCode;

	public String errMsg;

	public Map<Integer,Integer> errList;

	public String payLink;

	public ArrayList<AsyncContext> statusChangeListenners;

	public OrderGroupPayResult(OrderGroup orderGroup){
		this.orderGroup = orderGroup;
	}


	public int getPayResult(){
		if(orderGroup.statu == Order.OrderStatu.waitpay.getStatus())
			return 2;

		if(orderGroup.statu == Order.OrderStatu.payFail.getStatus())
			return 1;

		if(orderGroup.statu == Order.OrderStatu.paySuccess.getStatus())
			return 0;
		return 1;
	}

	public void addStatusChangeListenner(AsyncContext context){
		if(statusChangeListenners == null){
			statusChangeListenners = new ArrayList<>();
		}
		statusChangeListenners.add(context);
	}


	public void notifyStatusChange(){
		if(statusChangeListenners != null){
			Iterator<AsyncContext> iterator = statusChangeListenners.iterator();
			while (iterator.hasNext()){
				AsyncContext ctx = iterator.next();
				ctx.complete();
			}
			statusChangeListenners.clear();
		}
	}


	public interface OrderResultCode{

		interface GetOrder{
			int Lottery_Not_Exist = 1001;
			int Successful = 1002;
			int Exception = 1003;
		}

		interface CreateOrder{
			int CREATE_FAIL = -1 ;
			int NO_STOCK = -2 ;
			int Exception = -3 ;
			int CREAT_PAY_LINK_ERROR = -4;
		}
	}
}
