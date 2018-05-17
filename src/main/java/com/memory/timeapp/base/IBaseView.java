package com.memory.timeapp.base;

/**
 * Created by mr.miao on 2018/5/7.
 */

public interface IBaseView {
    /**
     * 加载失败
     *
     * @param message
     */
    void loadFailure(String message);

    /**
     * 开始加载
     */
    void loadStart();

    /**
     * 加载结束
     */
    void loadComplete();
}
