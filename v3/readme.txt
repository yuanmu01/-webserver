此版本测试读取请求的第一行字符串
由于客户端发送的请求（Request）的请求行和消息头部分都是有CRLF
结尾的一行字符串形式的内容 因此为了编译后续实现解析请求的工作
在这个版本先实现一个读取一行字符串的算法

实现
在ClinetHandler第一步解析请求的环节 将之前读取请求所有内容的
操作改为读取一行字符串的操作
