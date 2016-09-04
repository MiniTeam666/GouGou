package com.yyg.utils;

import com.yyg.model.Order;
import com.yyg.service.ProductService;

/**
 * Created by line on 2016/9/4.
 */
public class OrderTimeoutRunnable implements Runnable{

    private Order order;

    private long timeout;

    private boolean cancel;

    private volatile boolean start;

    private ProductService service;

    public OrderTimeoutRunnable(Order order, long timeout, ProductService service){
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
            order.state = Order.OrderStatu.timeout.getStatus();
            service.notifyOrderPayResult(order,false);
        }else {
            start = false;
        }
    }
}
