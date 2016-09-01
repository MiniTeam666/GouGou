package com.yyg;

import java.util.concurrent.*;

/**
 * Created by line on 2016/8/26.
 */
public class CacheManager {

    public static ScheduledExecutorService mExecutor =  Executors.newScheduledThreadPool(AppConstant.CACHE_THREAD_NUM);

    private ConcurrentHashMap<String,CacheField> mCache = new ConcurrentHashMap<String,CacheField>();

    public CacheField get(String key){
        return mCache.get(key);
    }

    public void put(String key,CacheField value){
        mCache.put(key,value);
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
