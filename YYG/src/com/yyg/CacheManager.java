package com.yyg;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.yyg.model.Lottery;
import com.yyg.model.Order;
import com.yyg.service.ProductService;
import com.yyg.utils.LotteryInfo;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by line on 2016/8/26.
 */
public class CacheManager {

    private CacheField<ConcurrentHashMap<Integer,Lottery>> mLotteriesCache;

    private ConcurrentHashMap<Integer,List<Order>> mOrderCache;

    private static volatile CacheManager instance;


    private CacheManager(){
        mOrderCache = new ConcurrentHashMap<>();
    }

    public static CacheManager getInstance(){
        if(instance == null){
            synchronized (CacheManager.class){
                if(instance == null){
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }

    public List<Order> getOrders(int lotteryID){
    	return mOrderCache.get(lotteryID);
    }

    public void cacheOrder(Order order){
    	List<Order> orders = new ArrayList<>();
		orders.add(order);
    	cacheOrders(order.lottery.id,orders);
	}

    public void cacheOrders(int lotteryID,List<Order> orders){

    	if(orders == null || orders.isEmpty())
    		return;

    	List<Order> srcList = mOrderCache.get(lotteryID);
		for(Order order : orders) {
			if (!srcList.contains(order)) {
				srcList.add(order);
			} else {
				int index = srcList.indexOf(order);
				Order old = srcList.get(index);
				old.update(order);
			}
		}
    }


    public Lottery getLottery(int lotteryID){
        if(mLotteriesCache == null)
            return null;
        return mLotteriesCache.getData().get(lotteryID);
    }

    public synchronized void cacheLottery(Lottery lottery){
        if(lottery == null)
            return;

        if(mLotteriesCache == null){
            ArrayList<Lottery> list = new ArrayList<Lottery>();
            list.add(lottery);
            createLotteryCacheField(list);
        }else{
            ConcurrentHashMap<Integer,Lottery> data = mLotteriesCache.getData();
            Lottery old = data.get(lottery.id);
            if(old == null || old.lastJoinTime <= lottery.lastJoinTime){
                lottery.lotteryInfo = old == null ? new LotteryInfo(lottery) : old.lotteryInfo;
                data.put(lottery.id,lottery);
            }
        }
    }

    private synchronized void createLotteryCacheField(List<Lottery> list){

        ConcurrentHashMap<Integer,Lottery> tmp = new ConcurrentHashMap<Integer,Lottery>();
        int n = list.size();
        for(int i = 0; i < n ; i++){
            Lottery lottery = list.get(i);
            lottery.lotteryInfo = new LotteryInfo(lottery);
            tmp.put(lottery.id,lottery);
        }

        mLotteriesCache = new CacheField<ConcurrentHashMap<Integer, Lottery>>(tmp){
            @Override
            public void update(){
                ProductService productService = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
                List<Lottery> result = productService.getLotteriesWithoutCache();
                cacheLotteries(result);
            }
        };
    }

    public synchronized void cacheLotteries(List<Lottery> list){

        if(list == null || list.size() < 0)
            return;

        int n = list.size();
        if(mLotteriesCache == null){
            createLotteryCacheField(list);
        }else{
            ConcurrentHashMap<Integer,Lottery> data = mLotteriesCache.getData();
            for(int i = 0; i < n; i++){
                Lottery newValue = list.get(i);
                Lottery oldValue = data.get(newValue.id);
                if(oldValue == null || oldValue.lastJoinTime <= newValue.lastJoinTime){
                    newValue.lotteryInfo = oldValue == null ? new LotteryInfo(newValue) : oldValue.lotteryInfo;
                    data.put(newValue.id,newValue);
                }
            }
        }
    }

    public List<Lottery> getLotteries(){
        if(mLotteriesCache != null) {
            Collection<Lottery> collections = mLotteriesCache.getData().values();
            ArrayList<Lottery> result = new ArrayList<Lottery>();
            result.addAll(collections);
            return result;
        }
        return null;
    }


    public static class CacheField<T> implements Runnable{

        private long mLastUpdateTime;

        private int mDuration;

        private boolean mAutoUpdate;

        private T data;

        public CacheField(T data, int duration, final boolean autoUpdate){
            mLastUpdateTime = -1;
            mDuration = duration;
            mAutoUpdate = autoUpdate;
            this.data = data;
            if(autoUpdate){
                ThreadManager.executeOnUpdateThread(this,duration);
            }
        }

        public CacheField(T data){
            this(data,AppConstant.DEFAULT_REFRESH_DURATION,false);
        }

        public void updateInnner(){
            LogManager.getLogger().info("update cache field ! ");
            update();
            mLastUpdateTime = System.currentTimeMillis();
        }

        public void update(){
            //do something
        }

        public T getData(){
            long now = System.currentTimeMillis();
            if(!mAutoUpdate && now - mLastUpdateTime >= mDuration) {
                //每次取得数据检查下是否要更新缓存
                ThreadManager.executeOnUpdateThread(this);
            }
            return data;
        }

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            if(now - mLastUpdateTime >= mDuration) {
                updateInnner();
            }

            if(mAutoUpdate){
                ThreadManager.executeOnUpdateThread(this,mDuration);
            }
        }
    }

}
