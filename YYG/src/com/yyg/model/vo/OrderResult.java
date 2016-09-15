package com.yyg.model.vo;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by line on 2016/9/15.
 */
public class OrderResult {

	public boolean success;

	public int errCode;

	public String errMsg;

	public JSONArray array;

	public JSONObject object;

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
		}
	}
}
