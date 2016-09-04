package com.yyg.utils;

import com.yyg.AppConstant;
import com.yyg.model.Order;

/**
 * Created by line on 2016/9/3.
 */
public class Message {

    public int event;

    public int what;

    public int result;

    public Object obj;

    public static Message getUpdateStockMsg(int id,int result,Order order){
        Message msg = new Message();
        msg.event = AppConstant.EVENT_UPDATE_STOCK;
        msg.what = id;
        msg.result = result;
        msg.obj = order;
        return msg;
    }
}
