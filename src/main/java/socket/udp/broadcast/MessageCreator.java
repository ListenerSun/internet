package socket.udp.broadcast;

/**
 * @Description:
 * @author: ListenerSun(男, 未婚) 微信:810548252
 * @Date: Created in 2019-10-04 17:32
 */
public class MessageCreator {
    private static final String ME_PREFIX = "收到暗号,我是(SN):";
    private static final String PORT_PREFIX = "这是暗号,请回电端口(Port):";

    /**
     * 构建端口暗号
     * @param port
     * @return
     */
    public static String buildWithPort(int port) {
        return PORT_PREFIX + port;
    }

    /**
     * 解析端口
     * @param data
     * @return
     */
    public static int parsePort(String data){
        if (data.startsWith(PORT_PREFIX)){
            return Integer.parseInt(data.substring(PORT_PREFIX.length()));
        }
        return -1;
    }

    /**
     *
     * @param data
     * @return
     */
    public static String buildData(String data){
        return  ME_PREFIX+data;
    }

    /**
     *
     * @return
     */
    public static String parseData(String data){
        if (data.startsWith(ME_PREFIX)){
            return data.substring(ME_PREFIX.length());
        }
        return null;
    }
}
