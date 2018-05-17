package com.memory.timeapp.base;

import java.util.List;

/**
 * Created by mr.miao on 2018/5/7.
 */

public interface BaseLoadListener<T> {
    /**
     * 加载数据成功
     *
     * @param list
     */
    void loadSuccess(List<T> list);

    void loadSuccess(T t);


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
