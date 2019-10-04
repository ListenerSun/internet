package socket.udp.broadcast;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: UDP 搜索方
 * @author: ListenerSun(男, 未婚) 微信:810548252
 * @Date: Created in 2019-10-04 15:41
 */
@Slf4j
public class UDPSearchBroad {
    private static final int LISTENER_PORT = 30000;

    public static void main(String[] args) throws IOException {
        Listener listen = listen();
        //发送广播
        sendBroadcast();
        System.in.read();
        List<Device> deviceList = listen.getDevicesAndClose();
        log.info("deviceList:{}", deviceList);

    }

    private static Listener listen() {
        Listener listener = new Listener(LISTENER_PORT);
        listener.start();
        return listener;
    }

    private static void sendBroadcast() throws IOException {
        log.info("UDPSearchBroad send BroadCast start......");
        DatagramSocket ds = new DatagramSocket();
        //构建一份发送数据
        String requestData = MessageCreator.buildWithPort(LISTENER_PORT);
        byte[] requestDataDateBytes = requestData.getBytes();
        DatagramPacket resquestPacket = new DatagramPacket(requestDataDateBytes,
                requestDataDateBytes.length,
                Inet4Address.getByName("255.255.255.255"),
                20000);
        ds.send(resquestPacket);
        ds.close();
        log.info("UDPSearchBroad send BroadCast finshed......");
    }

    private static class Listener extends Thread {
        private int listenPort;
        private List<Device> deviceList = new ArrayList<>();
        private DatagramSocket ds;
        private boolean done = false;

        public Listener() {
            super();
        }

        public Listener(int listenPort) {
            this.listenPort = listenPort;
        }

        @Override
        public void run() {
            super.run();
            log.info("UDPSearchBroad listen start......");
            //作为搜索放 无需指定端口
            try {
                ds = new DatagramSocket(listenPort);
                while (!done) {
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
                    String ms = MessageCreator.parseData(data);
                    if (null != ms) {
                        Device device = new Device(ip, port, data);
                        deviceList.add(device);
                    }

                }

            } catch (IOException e) {
            } finally {
                close();
            }
            //完成
            log.info("UDPSearchBroad finshed......");
        }

        /**
         * 关闭
         */
        void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        List<Device> getDevicesAndClose() {
            done = true;
            close();
            return deviceList;
        }
    }

    /**
     * 设备类
     */
    @AllArgsConstructor
    @ToString
    private static class Device {
        final private String ip;
        final private int port;
        final private String ms;
        private boolean done;

        public Device(String ip, int port, String ms) {
            this.ip = ip;
            this.port = port;
            this.ms = ms;
        }
    }
}
