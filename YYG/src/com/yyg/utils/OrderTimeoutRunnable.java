package com.yyg.utils;

import com.yyg.model.Order;
import com.yyg.service.OrderService;
import com.yyg.service.ProductService;

/**
 * Created by line on 2016/9/4.
 */
public class OrderTimeoutRunnable implements Runnable{

    private Order order;

    private long timeout;

    private boolean cancel;

    private volatile boolean start;

    private OrderService service;

    public OrderTimeoutRunnable(Order order, long timeout, OrderService service){
        this.service = service;
        this.timeout = timeout;
        this.order = order;
    }

    public boolean cancel(){
        cancel = true;
        if(start)
            return false;
        return true;
    }

    @Override
    public void run() {
        if(!cancel){
            start = true;
            order.state = Order.OrderStatu.payFail.getStatus();
            service.notifyOrderPayResult(order,Message.ERROR_CODE_ORDERE_PAY_TIMEOUT);
        }else {
            start = false;
        }
    }
}
