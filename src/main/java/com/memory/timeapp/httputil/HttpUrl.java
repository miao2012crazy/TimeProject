package com.memory.timeapp.httputil;


import com.memory.timeapp.constant.AppConstant;

/**
 * Created by Administrator on 2017/9/26.
 */

public class HttpUrl {

    public  static String SOCKET_URL="10.13.20.137";
    private static String baseUrl="";
    public static String getBaseUrl() {
        if (AppConstant.isDebug){
//            return "http://192.168.0.103:8080/";
            return "http://10.13.20.137:8080/";
        }
        return baseUrl;
    }

}
