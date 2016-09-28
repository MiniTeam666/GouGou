package com.yyg.model.vo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by line on 2016/9/15.
 */
public class OrderResult {

	public boolean success;

	public int errCode;

	public int orderID:

	public String errMsg;

	public Map<Integer,Integer> errList;

	public String payLink;

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
