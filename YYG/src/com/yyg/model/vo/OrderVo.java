package com.yyg.model.vo;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.yyg.model.Order;
import com.yyg.utils.YYGUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by line on 2016/9/7.
 */
public class OrderVo {

	private String userName;

	private int userID,orderID;

	private int buyCnt;

	private long buyTime;

	public OrderVo(Order order){
		buyTime = order.time;
		buyCnt = order.joinTime;
		userName = order.user.name;
		userID = order.user.id;
		orderID = order.id;
	}

	public JSONObject getJsonData(){
		JSONObject data = new JSONObject();
		try{
			data.put("name",userName);
			data.put("joins",buyCnt);
			data.put("join_time", YYGUtils.getTimeStr(buyTime));
			data.put("user_id",userID);
			data.put("record_id",orderID);
		}catch (Exception e){
			e.printStackTrace();
		}
		return data;
	}

	public static List<OrderVo> translateOrders(List<Order> orders){
		ArrayList<OrderVo> orderVos = new ArrayList<>();
		for(Order order : orders){
			orderVos.add(new OrderVo(order));
		}
		return orderVos;
	}
}
