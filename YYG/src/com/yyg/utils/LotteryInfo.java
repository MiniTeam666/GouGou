package com.yyg.utils;

import com.yyg.AppConstant;
import com.yyg.CacheManager;
import com.yyg.ServiceManager;
import com.yyg.model.Lottery;
import com.yyg.model.Order;
import com.yyg.service.OrderService;
import com.yyg.service.ProductService;
import org.apache.logging.log4j.LogManager;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by line on 2016/9/3.
 */
public class LotteryInfo implements Observer{

    private Lottery lottery;

    private volatile int realStock;

    private volatile int copyStock;

    private volatile int luckyNumBitmap;

    private int id;

    public LotteryInfo(Lottery lottery){
        this.lottery = lottery;
        this.id = lottery.id;
        realStock = lottery.remainCountOfQulification;
        copyStock = lottery.remainCountOfQulification;
    }

    private synchronized boolean incremeCopyStock(int delta){
        if(copyStock + delta < 0 || copyStock + delta > realStock)
            return false;
        copyStock += delta;
        return true;
    }

    private synchronized boolean incrementRealStock(int delta){
        if(realStock + delta < 0)
            return false;
        realStock += delta;

        return true;
    }

    private synchronized boolean updateStockInfo(int delta){
        if(!incrementRealStock(delta))
            return false;
        lottery.remainCountOfQulification = realStock;
        lottery.lastJoinTime = System.currentTimeMillis();
        lottery.buyRecord = YYGUtils.getBuyRecord(lottery.buyRecord,delta,lottery.lastJoinTime);
        if(lottery.remainCountOfQulification == 0){
            lottery.status = Lottery.LotteryStatu.inLottery.getStatus();
            lottery.lotteryTime = lottery.lastJoinTime - AppConstant.LOTTERY_DELAY_TIME_MILL;
        }
        CacheManager.getInstance().cacheLottery(lottery);

        //TODO 定时刷新数据库

        return true;
    }

    public int decrementStock(int count){
        boolean ret = incremeCopyStock(-count);
        if(!ret){
            if(realStock == 0)
                return 1;
            return 2;
        }
        return 0;
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof Message){
            Message msg = (Message) data;
            if(msg.event != AppConstant.EVENT_UPDATE_STOCK || msg.what != id)
                return;

			OrderService orderService = (OrderService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
            Order order = (Order) msg.obj;
            int value = order.joinTime;
            if(AppConstant.OK == msg.result){
                int oldValue = realStock;
                int newValue = realStock - value;
                if(newValue < 0){
                    LogManager.getLogger().error("id : " + id + " has appear positive stock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }

                boolean ret = updateStockInfo(-value);
                if(!ret){
                    LogManager.getLogger().error("id : " + id + " has appear positive stock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }else {
                    order.state = Order.OrderStatu.paySuccess.getStatus();
                }

            }else{

                order.state = Order.OrderStatu.payFail.getStatus();


                int oldValue = copyStock;
                int newValue = copyStock + value;
                if(newValue > realStock){
                    LogManager.getLogger().error("id : " + id + " has appear positive stock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }

                boolean ret = incremeCopyStock(value);
                if(!ret){
                    LogManager.getLogger().error("id : " + id + " has appear unnormal copyStock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }

            }

            if(msg.result != Message.ERROR_CODE_ORDERE_CREATE_FAIL)
                orderService.updateOrder(order);
        }
    }
}
