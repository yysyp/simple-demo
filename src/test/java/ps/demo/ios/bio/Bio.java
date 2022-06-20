package ps.demo.ios.bio;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bio {
    static boolean stop = false;

    public static void main(String[] args) throws Exception {
        int connectionNum = 0;
        int port = 8888;
        ExecutorService service = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(port);
        while (!stop) {
            if (10 == connectionNum) {
                stop = true;
            }
            Socket socket = serverSocket.accept();
            service.execute(() -> {
                try {
                    Scanner scanner = new Scanner(socket.getInputStream());
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                    while (!stop) {
                        String s = scanner.next().trim();
                        printStream.println("BIO-ECHO:" + s);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            connectionNum++;
        }
        service.shutdown();
        serverSocket.close();
    }
}
