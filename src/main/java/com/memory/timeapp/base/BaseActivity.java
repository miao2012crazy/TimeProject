package com.memory.timeapp.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.memory.timeapp.commonUtil.UIUtils;
import com.memory.timeapp.handlerevent.HandlerEvent;


/**
 * Activity 基类
 * Created by mr.miao on 2018/4/23.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView{


    private ViewDataBinding viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentStatusBar() .init();
        viewDataBinding = DataBindingUtil.setContentView(this, setLayoutId());
        initBinding(viewDataBinding);
    }

    /**
     * 初始化绑定
     * @param viewDataBinding 可以直接强转
     */
    protected abstract void initBinding(ViewDataBinding viewDataBinding);


    /**
     * 设置布局id
     * @return
     */
    protected abstract int setLayoutId();

    //启动页面不带参数 直接启动
    protected void startActivityBase(Class clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    @Override
    public void loadFailure(String message) {
        UIUtils.showToast("加载失败！");
    }

    @Override
    public void loadStart() {
        UIUtils.showToast("加载开始！");
    }

    @Override
    public void loadComplete() {
        UIUtils.showToast("加载完成！");
    }


    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
