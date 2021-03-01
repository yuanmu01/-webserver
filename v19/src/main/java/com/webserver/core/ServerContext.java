package com.webserver.core;

import com.webserver.servlet.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前类用于保存服务端重用的一些内容
 */
public class ServerContext {
 private static Map<String, HttpServlet> servletMapping = new HashMap<>();

 static {
     initServletMapping();
 }
 private static void initServletMapping()  {
   /*
   解析config/servlets.xml文件 将根标签下所有名为<servlet>的
   标签获取到 并将其属性的path值作为key  className的值利用反射
   实例化对应的类并作为value 保存到servletMapping 这个Map完成初始化操作
    */


     try {
         SAXReader reader = new SAXReader();
         Document doc = reader.read("./config/servlet.xml");
         Element root = doc.getRootElement();
         List<Element> list = root.elements("servlet");
        for (Element servletEle : list){
           String path =  servletEle.attributeValue("path");
           String className =  servletEle.attributeValue("className");
             Class cls = Class.forName(className);
            HttpServlet servlet = (HttpServlet)cls.newInstance();
            servletMapping.put(path,servlet);
        }

     } catch (Exception e) {
         e.printStackTrace();
     }


//     servletMapping.put("/myweb/regUser",new RegServlet());
//     servletMapping.put("/myweb/loginUser",new LoginServlet());
//     servletMapping.put("/myweb/showAlluser",new ShowAllUserServelet());
//     servletMapping.put("/myweb/toUpdate",new ToUpdateServlet());
 }

 public static HttpServlet getServlet(String path){
     return  servletMapping.get(path);
 }

}
