package com.memory.timeapp.constant;

/**
 * Created by mr.miao on 2018/4/28.
 */

public class MessageEvent {
    private String message;
    private int code;

    public MessageEvent(int code,String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
