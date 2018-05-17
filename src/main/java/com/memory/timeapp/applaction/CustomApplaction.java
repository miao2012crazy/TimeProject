package com.memory.timeapp.applaction;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * Created by mr.miao on 2018/4/23.
 */

public class CustomApplaction extends Application {
    private static Context context;
    private static Handler handler;
    private static Thread mainThread;
    private static int mainThreadId;
    private static CustomApplaction app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //在此方法中需要获取handler对象,上下文环境
        context = getApplicationContext();
        //准备handler对象
        handler = new Handler();
        //hanlder应用场景(子线程往主线程中发送message)

        //获取主线程的对象,WLBApplication的onCreate运行在主线程中的代码
        mainThread = Thread.currentThread();
        //获取主线程(当前线程)id方法
        mainThreadId = android.os.Process.myTid();
        mMainThreadHandler = new Handler();

//        SocketUtil.initSocket(new SocketUtil.OnInitSocketListener() {
//            @Override
//            public void onInitSuccess(Socket socket) {
//
//            }
//
//            @Override
//            public void onInitFailed(String errStr) {
//                UIUtils.showToast(errStr);
//            }
//        });
    }


    /**
     *  获取上下文对象
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取handler对象
     * @return
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程
     * @return
     */
    public static Thread getMainThread() {
        return mainThread;
    }

    /**
     * 获取主线程id
     * @return
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    private static Handler mMainThreadHandler = null;

    /**
     * 获取主线程handler对象
     * @return
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }





}
