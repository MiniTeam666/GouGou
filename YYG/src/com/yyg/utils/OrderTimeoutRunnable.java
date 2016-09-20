package com.yyg.utils;

import com.yyg.model.Order;
import com.yyg.model.OrderGroup;
import com.yyg.service.OrderService;
import com.yyg.service.ProductService;
import org.apache.logging.log4j.LogManager;

import java.util.Iterator;

/**
 * Created by line on 2016/9/4.
 */
public class OrderTimeoutRunnable implements Runnable{

    private Order order;

	private OrderGroup orderGroup;

    private long timeout;

    private boolean cancel = false;

    private volatile boolean start;

    private OrderService service;

    public OrderTimeoutRunnable(Order order, long timeout, OrderService service){
        this.service = service;
        this.timeout = timeout;
        this.order = order;
    }

	public OrderTimeoutRunnable(OrderGroup orderGroup, long timeout, OrderService service){
		this.service = service;
		this.timeout = timeout;
		this.orderGroup = orderGroup;
	}

    public boolean cancel(){
        cancel = true;
        if(start)
            return false;
        return true;
    }

    @Override
    public void run() {
		LogManager.getLogger().info("timeout runnable run !");
        if(!cancel){
            start = true;
			Iterator<Order> orderIterator = orderGroup.orders.iterator();
			while(orderIterator.hasNext()){
				Order order = orderIterator.next();
				order.state = Order.OrderStatu.payFail.getStatus();
			}
			service.notifyOrderPayResult(orderGroup,Message.ERROR_CODE_ORDERE_PAY_TIMEOUT);
        }else {
            start = false;
        }
    }
}
