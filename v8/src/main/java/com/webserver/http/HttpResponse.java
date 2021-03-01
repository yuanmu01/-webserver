package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 响应对象 当前类的每一个实例用于表示给客户端发送一个HTTP
 * 每个响应有三部分构成
 * 状态行 响应头 响应正文（正文部分可以没有）
 */
public class HttpResponse {

    private Socket socket;

  public HttpResponse(Socket socket){
      this.socket=socket;
  }
    //状态行相关信息
   private int statusCode = 200;//状态代码默认值200 因为绝大多数请求实际应用中都能正确处理
    private String statusReason = "OK";

    //响应头相关信息

    //响应正文相关信息
    private File entity;
    public void flush(){
    /*
    发送一个响应时 按顺序发送状态行 响应头 响应正文

     */
        sendStatusLine();
        sendHeaders();
        sendContent();
    }
    // 发送一个响应的三个步骤
    // 发送状态行
    private void sendStatusLine(){
        System.out.println("开始发送状态行");
        try {
            String line = "HTTP/1.1" + " " + statusCode + " " + statusReason;
            System.out.println("状态行"+line);
            println(line);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("状态行发送完毕");
    }
    //发送响应头
    private void sendHeaders(){
        System.out.println("开始发送响应头");
        try {
            String line = "Content-Type: text/html";
            System.out.println("响应头"+line);
            println(line);

            line = "Content-Length: " + entity.length();
            System.out.println("响应头"+line);
            println(line);
            //单独发送CRLF表示响应头部分发送完毕!
           println("");
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("响应头发送完毕");
    }
    // 发送响应正文
    private void sendContent(){
        System.out.println("开始发送响应正文");
        try(
                FileInputStream fis = new FileInputStream(entity);
        ){
            OutputStream out = socket.getOutputStream();
            int len;//每次读取的字节数
            byte[] buf = new byte[1024*10];//10kb字节数组
            while((len = fis.read(buf))!=-1){
                out.write(buf,0,len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("响应正文发送完毕");
    }
    private void  println(String line ) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] data = line.getBytes("ISO8859-1");
        out.write(data);
        out.write(13);//单独发送回车符
        out.write(10);//单独发送换行符
    }

    public File getEntity(){
        return entity;
    }

    public  void setEntity(File entity){
        this.entity = entity;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}
