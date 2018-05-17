package com.memory.timeapp.commonUtil;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by mr.miao on 2018/5/14.
 */

public class SocketUtil {

    private static Socket socket;

    public static void initSocket(final OnInitSocketListener onInitSocketListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("10.13.20.137", 8888);
                    onInitSocketListener.onInitSuccess(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                    onInitSocketListener.onInitFailed("主机错误");
                }
            }
        }).start();
    }


    /**
     * 初始化socket
     */
    public static void getSocket(OnInitSocketListener onInitSocketListener) {
        if (socket != null && socket.isConnected()) {
            onInitSocketListener.onInitSuccess(socket);
        } else {
            initSocket(onInitSocketListener);
        }
    }


    public interface OnInitSocketListener{
        void onInitSuccess(Socket socket);
        void onInitFailed(String errStr);
    }


    /**
     * 接收数据
     */
    public static void getDataFromServer(final Socket socket) {
        if (socket == null) {
            return;
        }

        if (!socket.isConnected()) {
            Log.e("miao", "socket未连接");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = "";
                try {
                    // 步骤1：创建输入流对象InputStream
                    InputStream is = socket.getInputStream();
                    // 步骤2：创建输入流读取器对象 并传入输入流对象
                    // 该对象作用：获取服务器返回的数据
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
                    while (true) {
                        data = br.readLine();
                        Log.e("miao接收到数据", data);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 发送数据到服务器
     *
     * @param socket
     * @param data
     */
    public static void sendDataToServer(Socket socket, String data) {
        if (socket == null) {
            return;
        }

        if (!socket.isConnected()) {
            Log.e("miao", "socket未连接");
            return;
        }

        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write((data + "\n").getBytes("utf-8"));
            // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞
            // 步骤3：发送数据到服务端 刷新缓冲区
            outputStream.flush();
            UIUtils.showToast("数据发送");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
