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
public class LotteryBuyController implements Observer{

    private Lottery lottery;

    private volatile int realStock;

    private volatile int copyStock;

    private volatile int[] luckyNumBitmap;

    private int id;

	private int price;

    public LotteryBuyController(Lottery lottery){
        this.lottery = lottery;
        this.id = lottery.id;
        realStock = lottery.remainCountOfQulification;
        copyStock = lottery.remainCountOfQulification;
		luckyNumBitmap = YYGUtils.hex2Int(lottery.luckNumBitmap);
		price = lottery.product.price;
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

    private synchronized String updateStockInfo(int delta){
        if(!incrementRealStock(-delta))
            return null;

        lottery.remainCountOfQulification = realStock;
        lottery.lastJoinTime = System.currentTimeMillis();
        lottery.buyRecord = YYGUtils.getBuyRecord(lottery.buyRecord,delta,lottery.lastJoinTime);

		String luckNum = YYGUtils.getLuckNum(luckyNumBitmap,price,delta);
		lottery.luckNumBitmap = YYGUtils.int2Hex(luckyNumBitmap);

        if(lottery.remainCountOfQulification == 0){
            lottery.status = Lottery.LotteryStatu.inLottery.getStatus();
            lottery.lotteryTime = lottery.lastJoinTime + AppConstant.LOTTERY_DELAY_TIME_MILL;
        }


		CacheManager.getInstance().cacheLottery(lottery);

		/** 异步刷新数据库 */
		//TODO 考虑异步刷新任务失败如何ReToDo
		ProductService service = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
        service.updateLotteryAsync(lottery);

        return luckNum;
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

                String luckNum = updateStockInfo(value);
                if(YYGUtils.isEmptyText(luckNum)){
                    LogManager.getLogger().error("id : " + id + " has appear positive stock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }else {
                    order.state = Order.OrderStatu.paySuccess.getStatus();
					order.luckNum = luckNum;
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
