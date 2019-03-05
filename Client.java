import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        //1. Socket객체생성
        Socket socket = new Socket();
        //2. 设置超时时间
        socket.setSoTimeout(3000);
        //3. 连接本地，端口2000；超时时间3000ms
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);

        //4. 输出信息： 已发起服务器莲姐姐，并进入后续流程
        System.out.println("已发起服务器莲姐姐，并进入后续流程");
        //5. 输出信息：
        System.out.println("客户端信息：" + socket.getLocalAddress() + " P:" + socket.getLocalPort());
        //6. 输出信息：
        System.out.println("服务器信息：" + socket.getInetAddress() + " P:" + socket.getPort());
        //7. todo()
        try {
            todo(socket);
        } catch (Exception e) {
            System.out.println("异常关闭");
        }

        socket.close();
        System.out.println("客户端已退出");


    }

    private static void todo(Socket client) throws IOException {
        //1. 键盘输入流를 받는다
        InputStream in = System.in;
        //2. BufferedReader  输入流
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        //3. 拿到客户端的输出流
        OutputStream outputStream = client.getOutputStream();
        //4. 转换成打印流
        PrintStream socketPrintStream = new PrintStream(outputStream);

        InputStream socketInputStream = client.getInputStream();
        BufferedReader socketbufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));

        boolean flag = true;
        do {
            //5. 读取一行
            String str = input.readLine();

            //6. 发送数据
            socketPrintStream.println(str);

            String echo = socketbufferedReader.readLine();

            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        } while (flag);

        socketPrintStream.close();
        socketbufferedReader.close();


    }


}
