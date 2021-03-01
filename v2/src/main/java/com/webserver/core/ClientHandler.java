package com.webserver.core;

import com.sun.istack.internal.FinalArrayList;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 负责与指定客户端进行HTTP交互
 * HTTP协议要求与客户端的交互格则采取一问一答的方式 因此
 * 处理客户端交互以三部形式完成
 * 1 解析请求（一问），
 * 2 处理请求
 * 3 发送响应（一答）
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run() {
//          1 解析请求（一问）
        try {
            InputStream in =socket.getInputStream();
            //2测试读取客户发送过来的请求内容
            int d;
            while ((d=in.read())!=-1){
                char c = (char)d;
                System.out.print(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//          2 处理请求
//          3 发送响应（一答)
        try {

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //           处理完毕后与客户断开异常
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
