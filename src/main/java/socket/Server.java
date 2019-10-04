package socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @Description:
 * @author: ListenerSun(男, 未婚) 微信:810548252
 * @Date: Created in 2019-10-01 19:48
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2000);
        log.info("服务器准备就绪......");
        log.info("服务器信息:{},port:{}", server.getInetAddress(), server.getLocalPort());
        for (; ; ) {
            Socket socket = server.accept();
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }

    }

    /**
     * 客户端处理器
     */
    public static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            log.info("新客户端连接,客户端地址:{},客户端port:{}", socket.getInetAddress(), socket.getPort());
            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                do{
                    //客户端拿到的数据
                    String str = bufferedReader.readLine();
                    if ("byebye".equalsIgnoreCase(str)){
                        flag = false;
                        log.info("客户端发送的内容是:{}",str);
                        printStream.println("byebye");
                    }else {
                        log.info("客户端发送的内容是:{}",str);
                        printStream.println(str);
                    }
                } while (flag);
                bufferedReader.close();
                printStream.close();
            } catch (IOException e) {
                log.error("链接异常断开");
            } finally {
                try {
                    socket.close();
                    log.info("客户端已断开,客户端信息:{},port:{}",socket.getInetAddress(),socket.getPort());
                } catch (IOException e) {
                    log.error("服务端关闭异常,异常信息:{}",e);
                }
            }
        }
    }
}
