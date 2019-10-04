package socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;

/**
 * @Description: 客户端
 * @author: ListenerSun(男, 未婚) 微信:810548252
 * @Date: Created in 2019-10-01 20:31
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket();
        client.setSoTimeout(3000);
        //链接本地 端口2000 超时时间 3000ms
        client.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);
        log.info("客户端开始发起服务器连接......");
        log.info("客户端信息:{},port:{}", client.getLocalAddress(), client.getLocalPort());
        log.info("服务端信息:{},port:{}", client.getInetAddress(), client.getPort());
        todo(client);
    }

    public static void todo(Socket socket) throws IOException {
        //构建键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        //得到 socket输出流 转换为打印流
        OutputStream outPut = socket.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outPut);

        //得到socket输入流 转换为BufferReader
        InputStream inputStream = socket.getInputStream();
        BufferedReader socketBufferreader = new BufferedReader(new InputStreamReader(inputStream));
        boolean flag = true;
        do {
            //键盘读取一行
            String content = input.readLine();
            //将内容发送到 服务器
            socketPrintStream.println(content);
            //从服务器返回的数据
            String result = socketBufferreader.readLine();
            if ("byebye".equalsIgnoreCase(result)) {
                log.info("服务端发回来的消息:{}", result);
                flag = false;
            } else {
                log.info("服务端发回来的消息:{}", result);
            }
        } while (flag);
        socketPrintStream.close();
        socketBufferreader.close();

    }
}
