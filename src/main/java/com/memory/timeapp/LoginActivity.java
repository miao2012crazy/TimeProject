package com.memory.timeapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.memory.timeapp.base.BaseActivity;
import com.memory.timeapp.databinding.ActivityLoginBinding;
import com.memory.timeapp.ui.CustomVideoView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by mr.miao on 2018/5/16.
 */

public class LoginActivity extends BaseActivity {


    private ActivityLoginBinding activityLoginBinding;
    private CustomVideoView customVideoView;

    @Override
    protected void initBinding(ViewDataBinding viewDataBinding) {
        activityLoginBinding = (ActivityLoginBinding)viewDataBinding;
        initData();
    }

    private void initData() {
        customVideoView = activityLoginBinding.customVideoView;
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                customVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bgvideo));
                customVideoView.start();
            }
        });



        //循环播放
        customVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                customVideoView.start();
            }
        });

        activityLoginBinding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityBase(MainActivity.class);
            }
        });


    }

    @Override
    protected void onRestart() {
        initData();
        super.onRestart();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }
    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        customVideoView.stopPlayback();
        super.onStop();
    }
}
