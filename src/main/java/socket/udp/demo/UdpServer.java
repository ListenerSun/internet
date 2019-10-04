package socket.udp.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @Description:UDP 提供者
 * @author: ListenerSun(男, 未婚) 微信:810548252
 * @Date: Created in 2019-10-04 15:39
 */
@Slf4j
public class UdpServer {

    public static void main(String[] args) throws IOException {
        log.info("UDPServer start......");
        //作为接收者，指定一个端口用来接收数据
        DatagramSocket ds = new DatagramSocket(20000);

        //构建接受实体
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
        //接收
        ds.receive(receivePack);
        //打印接收到的信息与发送者的信息
        //打印发送者的ip地址
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int dataLength = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, dataLength);
        log.info("UDPServer receive from ip:{},\tport:{},\tdata:{}", ip, port, data);

        //构建一份回送数据
        String responseData = "Receive data with len:" + dataLength;
        byte[] responseDateBytes = responseData.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseDateBytes,
                responseDateBytes.length,
                receivePack.getAddress(),
                receivePack.getPort());
        ds.send(responsePacket);
        //完成
        log.info("UDPServer finshed......");
        ds.close();

    }

}
