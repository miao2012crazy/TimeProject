package com.memory.timeapp;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.memory.timeapp.base.BaseActivity;
import com.memory.timeapp.commonUtil.UIUtils;
import com.memory.timeapp.databinding.ActivityMainBinding;
import com.memory.timeapp.handlerevent.HandlerEvent;
import com.memory.timeapp.view.IMainView;
import com.memory.timeapp.viewModel.ActivityMainViewModel;

public class MainActivity extends BaseActivity implements IMainView {

    @Override
    protected void initBinding(ViewDataBinding viewDataBinding) {
        ActivityMainBinding activityMainBinding = (ActivityMainBinding) viewDataBinding;
        ActivityMainViewModel activityMainViewModel = new ActivityMainViewModel(this, activityMainBinding);
        activityMainBinding.llTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.showToast("maisdais");
            }
        });
        HandlerEvent handlerEvent = new HandlerEvent(this);
        activityMainBinding.setHandler(handlerEvent);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }






}
