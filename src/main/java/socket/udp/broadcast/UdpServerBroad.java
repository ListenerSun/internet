package socket.udp.broadcast;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

/**
 * @Description:UDP 提供者
 * @author: ListenerSun(男, 未婚) 微信:810548252
 * @Date: Created in 2019-10-04 15:39
 */
@Slf4j
public class UdpServerBroad {

    public static void main(String[] args) throws IOException {
        String s = UUID.randomUUID().toString();
        Server server = new Server(s);
        server.start();
        System.in.read();
        server.exit();

    }

    public static class Server extends Thread {
        private final String ms;
        private boolean done = false;
        private DatagramSocket ds = null;

        public Server(String ms) {
            super();
            this.ms = ms;
        }

        @Override
        public void run() {
            super.run();
            log.info("UdpServerBroad start......");
            //作为接收者，指定一个端口用来接收数据
            try {
                ds = new DatagramSocket(20000);
                while (!done) {

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
                    String responseData = MessageCreator.buildData(ms);
                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1){
                        byte[] responseDateBytes = responseData.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(responseDateBytes,
                                responseDateBytes.length,
                                receivePack.getAddress(),
                                responsePort);
                        ds.send(responsePacket);
                    }

                }
            } catch (Exception ingored) {
            } finally {
                close();
            }
            //完成
            log.info("UdpServerBroad finshed......");
        }

        void close() {
            if (null != ds) {
                ds.close();
                ds = null;
            }
        }

        void exit() {
            done = true;
            close();
        }
    }

}
