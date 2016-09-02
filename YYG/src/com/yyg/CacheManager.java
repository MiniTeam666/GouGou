package com.yyg;

import com.j256.ormlite.stmt.query.In;
import com.yyg.model.Lottery;
import com.yyg.service.ProductService;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by line on 2016/8/26.
 */
public class CacheManager {

    public static ScheduledExecutorService mExecutor =  Executors.newScheduledThreadPool(AppConstant.CACHE_THREAD_NUM);

    private ConcurrentHashMap<String,CacheField> mCache = new ConcurrentHashMap<String,CacheField>();

    private CacheField<ConcurrentHashMap<Integer,Lottery>> mLotteriesCache;

    private static volatile CacheManager instance;

    private CacheManager(){
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

    public Lottery getLottery(int lotteryID){
        if(mLotteriesCache == null)
            return null;
        return mLotteriesCache.getData().get(lotteryID);
    }

    public void cacheLottery(Lottery lottery){
        if(lottery == null)
            return;

        if(mLotteriesCache == null){
            ArrayList<Lottery> list = new ArrayList<Lottery>();
            list.add(lottery);
            createLotteryCacheField(list);
        }else{
            ConcurrentHashMap<Integer,Lottery> data = mLotteriesCache.getData();
            Lottery old = data.get(lottery.id);
            if(old != null && old.lastJoinTime <= lottery.lastJoinTime){
                data.put(lottery.id,lottery);
            }
        }
    }

    private void createLotteryCacheField(List<Lottery> list){

        ConcurrentHashMap<Integer,Lottery> tmp = new ConcurrentHashMap<Integer,Lottery>();
        int n = list.size();
        for(int i = 0; i < n ; i++){
            tmp.put(list.get(i).id,list.get(i));
        }

        synchronized (mLotteriesCache){
            mLotteriesCache = new CacheField<ConcurrentHashMap<Integer, Lottery>>(tmp){
                @Override
                public void update(){
                    ProductService productService = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
                    List<Lottery> result = productService.getLotteriesWithoutCache();
                    cacheLotteries(result);
                }
            };
        }
    }

    public void cacheLotteries(List<Lottery> list){

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


    public static class CacheField<T>{

        private long mLastUpdateTime;

        private int mDuration;

        private boolean mAutoUpdate;

        private T data;

        public CacheField(T data,int duration,boolean autoUpdate){
            mLastUpdateTime = -1;
            mDuration = duration;
            mAutoUpdate = autoUpdate;
            this.data = data;
            if(autoUpdate){
                CacheManager.mExecutor.schedule(new Runnable() {
                    @Override
                    public void run() {
                        updateInnner();
                    }
                },duration,TimeUnit.MILLISECONDS);
            }
        }

        public CacheField(T data){
            this(data,1000,false);
        }

        public void updateInnner(){
            LogManager.getLogger().info("update cache field ! ");
            CacheManager.mExecutor.schedule(new Runnable() {
                @Override
                public void run() {
                    update();
                    mLastUpdateTime = System.currentTimeMillis();
                }
            },0, TimeUnit.SECONDS);
        }

        public void update(){
            //do something
        }

        public T getData(){
            long now = System.currentTimeMillis();
            if(!mAutoUpdate && now - mLastUpdateTime >= mDuration){
                updateInnner();
            }
            return data;
        }

    }

}
