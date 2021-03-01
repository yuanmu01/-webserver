重构HttpContext中初始化mineMapping的工作‘

通过解析config/web.xml文件将所有资源文件后缀与对应的Content-T
值存入HttpContext的静态属性mineMapping这个Map 这样一来
服务端就支持了所有的类型

实现：
重构HttpContext的方法：initMimeMapping
使用DOM4j读取config/web.xml文件
将跟标签下所有<mine-mapping>获取到 并将其中的子标签
<extension>中间的文本作为key
<mime-type>中间的文本作为value
存入mimeMappinge 这个Map完成初始化 初始化后mimenMapping
应该1011个元素