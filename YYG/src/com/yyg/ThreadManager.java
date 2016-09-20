package com.yyg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by line on 2016/9/3.
 */
public class ThreadManager {

    private static ExecutorService mSingleExecutor = Executors.newSingleThreadExecutor();

    private static ScheduledExecutorService mScheduleExecutor = Executors.newScheduledThreadPool(2);

    private static ScheduledExecutorService mUpdateExecutor =  Executors.newScheduledThreadPool(AppConstant.CACHE_THREAD_NUM);

	private static ExecutorService mSingleUpdateExecutor = Executors.newSingleThreadExecutor();

    public static void executeOnNormalThread(Runnable runnable){
        mSingleExecutor.execute(runnable);
    }

    public static void executeOnTimeoutThread(Runnable runnable,long timeout){
        mScheduleExecutor.schedule(runnable,timeout,TimeUnit.MILLISECONDS);
    }

    public static void executeOnUpdateThread(Runnable runnable,long timeout){
        mUpdateExecutor.schedule(runnable,timeout, TimeUnit.MILLISECONDS);
    }

    public static void executeOnUpdateThread(Runnable runnable){
        executeOnUpdateThread(runnable,0);
    }

    public static void executeOnSingleUpdateThread(Runnable runnable){
    	mSingleUpdateExecutor.execute(runnable);
	}

}
