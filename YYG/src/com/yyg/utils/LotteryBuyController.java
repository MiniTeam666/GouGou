package com.yyg.utils;

import com.yyg.AppConstant;
import com.yyg.CacheManager;
import com.yyg.ServiceManager;
import com.yyg.ThreadManager;
import com.yyg.model.Lottery;
import com.yyg.model.Order;
import com.yyg.service.OrderService;
import com.yyg.service.ProductService;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

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
		luckyNumBitmap = YYGUtils.hex2Int(new String(lottery.luckNumBitmap));
		price = lottery.product.price;

		OrderService service = (OrderService) ServiceManager.getInstance().getService(ServiceManager.Order_Service);
		service.addObserver(this);
    }

    private synchronized boolean incremeCopyStock(int delta){
        if(copyStock + delta < 0 || copyStock + delta > realStock)
            return false;
        copyStock += delta;
		LogManager.getLogger().info("copy stock change ! lottery : " + id
				+ ", realStock : " + realStock + ", copyStock : " + copyStock + ", cnt : " + delta);
        return true;
    }

    private synchronized boolean incrementRealStock(int delta){
        if(realStock + delta < 0)
            return false;
        realStock += delta;

		LogManager.getLogger().info("real stock change ! lottery : " + id
				+ ", realStock : " + realStock + ", copyStock : " + copyStock + ", cnt : " + delta);
        return true;
    }

    private synchronized String updateStockInfo(int count){

        if(!incrementRealStock(-count))
            return null;

        lottery.remainCountOfQulification = realStock;
        lottery.lastJoinTime = System.currentTimeMillis();
        lottery.buyRecord = YYGUtils.getBuyRecord(lottery.buyRecord,count,lottery.lastJoinTime);

		final String luckNum = YYGUtils.getLuckNum(luckyNumBitmap,price,count);
		lottery.luckNumBitmap = YYGUtils.int2Hex(luckyNumBitmap).getBytes();

        if(lottery.remainCountOfQulification == 0){

            lottery.status = Lottery.LotteryStatu.inLottery.getStatus();
            lottery.lotteryTime = lottery.lastJoinTime + AppConstant.LOTTERY_DELAY_TIME_MILL;

            ThreadManager.executeOnUpdateThread(new Runnable() {
                @Override
                public void run() {
                    ProductService productService = (ProductService) ServiceManager.getService(ServiceManager.Product_Service);
                    productService.handleProductLottery(lottery);
                }
            },AppConstant.LOTTERY_DELAY_TIME_MILL);

        }

		CacheManager.getInstance().cacheLottery(lottery);

		/** 异步刷新数据库 */
		//TODO 考虑异步刷新任务失败如何ReToDo
        ProductService productService = (ProductService) ServiceManager.getService(ServiceManager.Product_Service);
        productService.updateLotteryAsync(lottery);

		LogManager.getLogger().info("update lottery stock successful ! lottery : " + id
				+ ", real : " + realStock + ", copy : " + copyStock + ", cnt : " + count);

        return luckNum;
    }

    public int decrementStock(int count){
        boolean ret = incremeCopyStock(-count);
        if(!ret){
            if(realStock == 0)
                return 1;
            return 2;
        }
//        LogManager.getLogger().info("decrement copy stock successful ! lottery : " + id
//				+ ", realStock : " + realStock + ", copyStock : " + copyStock + ", cnt : " + count);
        return 0;
    }

    @Override
    public void update(Observable observable, Object data) {

        if(data instanceof Message){

            Message msg = (Message) data;
            if(msg.event != AppConstant.EVENT_UPDATE_STOCK || msg.what != id)
                return;

			OrderService orderService = (OrderService) ServiceManager.getService(ServiceManager.Order_Service);
            Order order = (Order) msg.obj;

            int value = order.joinTime;
            if(0 == msg.result){

                int oldValue = realStock;
                int newValue = realStock - value;

                String luckNum = updateStockInfo(value);
                if(YYGUtils.isEmptyText(luckNum)){
                    LogManager.getLogger().error("id : " + id + " has appear positive stock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }else {
                    order.state = Order.OrderStatu.paySuccess.getStatus();
					order.luckNums = luckNum.getBytes();
                }

                LogManager.getLogger().info("order is pay success ! order.id : " + order.id
						+ ", luckyNum : " + luckNum);
            }else{

                order.state = Order.OrderStatu.payFail.getStatus();

                int oldValue = copyStock;
                int newValue = copyStock + value;

                boolean ret = incremeCopyStock(value);
                if(!ret){
                    LogManager.getLogger().error("id : " + id + " has appear unnormal copyStock . now : " + oldValue + ", newValue : " + newValue + " , offset : " + value);
                    return;
                }

				LogManager.getLogger().info("order is pay fail ! order.id : " + order.id
						+ ", result : " + msg.result);
            }

            if(msg.result != Message.ERROR_CODE_ORDERE_CREATE_FAIL)
            	orderService.updateOrder(order);
        }
    }
}
