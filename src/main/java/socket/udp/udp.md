###UDP
- ####是什么？
    + 一种用户数据报协议,又称用户数据报文协议
    + 是一个简单的面向**数据报**的**传输层**协议,正式规范未RFC 768
    + 用户数据协议\非连接协议
- ####不可靠
    + 它一旦把应用程序发给网络层的数据发送出去，就不保留数据备份
    + UDP在IP数据的头部仅仅加入了复用和数据校验
    + 发送端产生数据，接收端从网络中抓取数据
    + 结构简单、无校验、速度快、容易丢包、可广播
- ####能做什么?
    + DNS、TFTP、SNMP
    + 视频、音频、普通数据(无关紧要数据
    + 一次最大传输65507字节长度
- ###例子 UDP搜索并且回送    