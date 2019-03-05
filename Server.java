import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(2000);
        //1. 输出信息： 已发起服务器莲姐姐，并进入后续流程
        System.out.println("服务器准备就绪~");
        //2. 输出信息：
        System.out.println("服务器信息：" + server.getInetAddress() + " P:" + server.getLocalPort());

        //等待客户端连接
        Socket client = server.accept();
        ClientHandler clientHandler = new ClientHandler(client);
        clientHandler.start();

    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println(socket.getInetAddress() + " P:" + socket.getPort());

            try {
                //得到打印流，用于数据输出，服务器会送数据使用
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());

                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                do {
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)) {
                        flag = false;
                        socketOutput.println("bye");
                    } else {
                        System.out.println(str);
                        socketOutput.println("长度:" + str.length());
                    }
                } while (flag);

                socketInput.close();
                socketOutput.close();

            } catch (Exception e) {
                System.out.println("连接异常断开");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }


}
