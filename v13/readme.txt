本版本开始完成对业务的支持
以用户注册为例 了解并实现服务端处理业务的操作
通过三个方面的知识完成本功能

1：浏览器如何提交用户在页面上输入的信息
2  服务端如何获取浏览器提交的表单数据
3  服务端如何处理注册（ClientHandler调用业务处理类）

实现
1 在实现webapps/myweb这个网络应用中添加一个新的页面
：reg.html 通过这个页面我们学习表单的使用

2: 重构HttpRequest
 在HttpRequest中添加三个新的属性：requestURI  queryString parameter
 并添加一个方法parseUri 用于将uir进一步拆分并存入上述三个属性
 然后在解析请求行的方法parseRequestLine中当解析请求行获得
 uri的值后 进一步拆分 最后作为三个属性添加get方法 以便外界可以
 通过请求对象获取用户在页面上输入的数据

 处理注册业务在下一个版本
