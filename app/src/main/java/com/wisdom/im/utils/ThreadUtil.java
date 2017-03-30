package com.wisdom.im.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lidongzhi on 2017/3/29.
 */

public class ThreadUtil {
    //Looper.myLoop()这样handler是在当前线程，Looper.getMainLooper()一定在主线程
    private static  Handler mHandler = new Handler(Looper.getMainLooper()); //指定looper为主线程

    public static void runOnUiThread(Runnable runnable){
        mHandler.post(runnable);
    }

    private static ExecutorService mExecutors = Executors.newSingleThreadExecutor();
    public static void runOnBgThread(Runnable runnable){
        mExecutors.execute(runnable);
    }
}
