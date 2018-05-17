package com.memory.timeapp.handlerevent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by mr.miao on 2018/5/9.
 */
public class HandlerEvent {

    private final Context mActivity;
    private Bundle bundle = new Bundle();


    public HandlerEvent(Context context) {
        this.mActivity = context;
    }

    //启动页面不带参数 直接启动
    private void startActivityBase(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * 带参数启动
     * @param context
     * @param clazz
     * @param bundle
     */
    private void startActivityBase(Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
