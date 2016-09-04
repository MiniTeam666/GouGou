package com.yyg.utils;

import com.yyg.AppConstant;
import com.yyg.ServiceManager;
import com.yyg.model.Lottery;
import com.yyg.model.Order;
import com.yyg.service.ProductService;
import org.apache.logging.log4j.LogManager;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by line on 2016/9/3.
 */
public class LotteryInfo implements Observer{

    private Lottery lottery;

    private AtomicInteger realStock;

    private AtomicInteger copyStock;

    private int id;

    public LotteryInfo(Lottery lottery){
        this.lottery = lottery;
        this.id = lottery.id;
        realStock = new AtomicInteger(lottery.remainCountOfQulification);
        copyStock = new AtomicInteger(lottery.remainCountOfQulification);
    }

    public synchronized  int decrementStock(int count){
        int oldValue = copyStock.get();
        int newValue = oldValue - count;
        if(newValue < 0){
            if(realStock.get() == 0)
                return 1;
            return 2;
        }

        if(!copyStock.compareAndSet(oldValue,newValue)){
            return 3;
        }
        return 0;
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof Message){
            Message msg = (Message) data;
            if(msg.event != AppConstant.EVENT_UPDATE_STOCK || msg.what != id)
                return;

            Order order = (Order) msg.obj;
            int value = order.joinTime;
            if(AppConstant.OK == msg.result){
                int oldValue = realStock.get();
                int newValue = realStock.get() - value;
                if(newValue < 0){
                    LogManager.getLogger().error("id : " + id + " has appear positive stock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }

                boolean ret = realStock.compareAndSet(oldValue,newValue);
                if(!ret){
                    while (value > 0){
                        value--;
                        newValue = realStock.decrementAndGet();
                    }
                }

                if(newValue < 0){
                    LogManager.getLogger().error("id : " + id + " has appear positive stock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }

                ProductService productService = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
                lottery = productService.getLottery(id);
                lottery.remainCountOfQulification = realStock.get();
                lottery.lastJoinTime = System.currentTimeMillis();
                lottery.buyRecord = YYGUtils.getBuyRecord(lottery.buyRecord,value,lottery.lastJoinTime);
                productService.updateLotteryStock(lottery);
            }else{
                int oldValue = copyStock.get();
                int newValue = copyStock.get() + value;
                if(newValue > realStock.get()){
                    LogManager.getLogger().error("id : " + id + " has appear positive stock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }

                boolean ret = copyStock.compareAndSet(oldValue,newValue);
                if(!ret){
                    while (value > 0){
                        value--;
                        newValue = copyStock.incrementAndGet();
                    }
                }

                if(newValue > realStock.get()){
                    LogManager.getLogger().error("id : " + id + " has appear unnormal copyStock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }
            }
        }
    }
}
