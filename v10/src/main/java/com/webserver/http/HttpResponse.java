package com.webserver.http;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 响应对象，当前类的每一个实例用于表示给客户端发送的一个HTTP响应
 * 每个响应由三部分构成:
 * 状态行，响应头，响应正文(正文部分可以没有)
 *
 */
public class HttpResponse  {
    private Socket socket;
    //状态行相关信息
     private int statusCode = 200;
     private String statusReason = "OK";
    //响应头相关信息
     Map<String,String> headers = new HashMap<String,String >();
    //响应正文相关信息
     private  File entity;
  public  HttpResponse(Socket socket){
      this.socket = socket;
  }
  public void flush(){
      sendStatusLine();
      sendHeaders();
      sendContent();
  }
    //1:发送状态行
     public  void sendStatusLine(){
      try {
          String line = "HTTP/1.1" + " " + statusCode + " " + statusReason;
             println(line);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    //2:发送响应头
    public  void sendHeaders(){
        try {
        Set<Map.Entry<String,String>> set = headers.entrySet();
        for (Map.Entry<String,String> e : set){
           String name = e.getKey();
           String value = e.getValue();
           String line = "Content-Type: " + name + value;
           println(line);
        }
            }catch (IOException ioException) {
                ioException.printStackTrace();
            }

    }

    //3:发送响应正文
    private void sendContent(){
        System.out.println("HttpResponse:开始发送响应正文...");
        try(
                //创建文件输入流读取要发送的文件数据
                FileInputStream fis = new FileInputStream(entity);
        ){
            OutputStream out = socket.getOutputStream();
            int len;//每次读取的字节数
            byte[] buf = new byte[1024*10];//10kb字节数组
            while((len = fis.read(buf))!=-1){
                out.write(buf,0,len);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("HttpResponse:响应正文发送完毕!");
    }

    public  void println(String line) throws IOException {

            OutputStream out = socket.getOutputStream();
            byte[] date = line.getBytes("ISO8859-1");
            out.write(date);
            out.write(13);
            out.write(10);

    }
    public void putHeader(String name, String value){
      headers.put(name,value);
    }

    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;
        String fileName = entity.getName();
        int d = fileName.lastIndexOf(".");
        String ext = fileName.substring(d+1);
        String value = HttpContext.getMimeType(ext);
        putHeader("Content-Type",value);
        putHeader("Content-Length",entity.length()+"");
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}






