package socket.udp.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;

/**
 * @Description: UDP 搜索方
 * @author: ListenerSun(男, 未婚) 微信:810548252
 * @Date: Created in 2019-10-04 15:41
 */
@Slf4j
public class UDPSearch {
    public static void main(String[] args) throws IOException {
        log.info("UDPSearchBroad start......");
        //作为搜索放 无需指定端口
        DatagramSocket ds = new DatagramSocket();
        //构建一份发送数据
        String requestData = "Hello sqt";
        byte[] requestDataDateBytes = requestData.getBytes();
        DatagramPacket resquestPacket = new DatagramPacket(requestDataDateBytes,
                requestDataDateBytes.length,
                Inet4Address.getLocalHost(),
                20000);
        ds.send(resquestPacket);

        //接收回送数据
        byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
        //接收
        ds.receive(receivePack);
        //打印接收到的信息与发送者的信息
        //打印发送者的ip地址
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int dataLength = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, dataLength);
        log.info("UDPSearchBroad receive from ip:{},\tport:{},\tdata:{}", ip, port, data);
        //完成
        log.info("UDPSearchBroad finshed......");
        ds.close();

    }
}
