将doc.canglaoshi.org网站上下载的学子商城或稻草问答的页面素材
导入webapps下并访问后发现页面不能正常显示
原因 无论浏览器请求什么资源 服务端在响应该资源时用于说明响应
正文的类型对应的响应头Content-Type的值都是固定的“text/thml”
这会导致浏览器不能正确理解其请求的资源从而无法发挥其应有的效果

分两个版本对响应中发送响应头的代码进行重构
此版本先解决第一个 发送固定的响应头
现在HttpResponse的sendHeaders方法中发送响应头是固定的：
Content-Type和Content-Length
实际应用中 服务端会集合实际情况发送多个响应头 虽然我们只需要上述
两个响应头 但是我们也要做到可以根据需求能发送多个响应头

思路
与请求一致 在HttpResponse中定义一个属性 Map headers 用这个
Map保存所有要给客户端发送的响应头 并在发送前可以结合实际需求
设置要发送的响应头 从而在flush时按需发送

实现
 在HttpResponse中添加属性 Map headers 并添加对应的设置响应头方法
 重构sendHeaders方法 通过遍历headers 将所有响应头发送给客户端
 在ClientHander处理请求的环节 设置response的响应头 并最终在flush
 时将设置的响应头发送给客户端 这里设置的响应头还是Content-Type和
 Content-Length即可

 第二个修改是可以根据请求的资源类型设置对应的Content-Type的值
 使得浏览器可以正确展示出页面

 实现
  在ClientHandler处理请求的环节添加一个Map 里面临时存放6中资源类型
  对应的Content-type的值 在向HTTPResponse添加响应头
  Content-Type时设置对应的值
  如此一来浏览器就可以正确展示页面了

