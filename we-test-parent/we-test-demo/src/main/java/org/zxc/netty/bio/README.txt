bio:block i/o
网络编程的基本模型是Client/Server模型，也就是两个进程之间进行通信，服务端提供位置信息（IP地址和端口），
客户端通过连接操作向服务端监听的地址发起连接请求，通过3次握手建立连接，如果连接建立成功，双方就可以通过
网络套接字(socket)进行通信。


传统的阻塞式IO会为每一个客户端创建一个新的线程进行链路处理，处理完成后将数据返回客户端，并销毁线程.
如果响应方比较缓慢，或者网络传输缓慢，那么读取方的IO线程将会被长时间阻塞。

 可以使用java自带的JVM 监控工具 Java VisualVM打印线程堆栈信息。 jvisualvm.exe 在JDK 的 bin 目录下，
 jdk1.6 update 7开始才有，双击即可启动。
 
伪异步IO：
使用服务端线程池优化传统的同步阻塞IO

关于InputStream的API说明：
当对socket的输入流进行读取操作的时候会一直阻塞下去，直到发生下列情况之一。
1，有数据可读
2，可用数据已经读取完毕
3，发生空指针或者IO异常

关于OutputStream的说明：
当使用OutputStream的write方法写输出流的时候，它将会被阻塞，直到所有要发送的字节写入完毕，或者发生异常。

NIO：Non-block I/O
