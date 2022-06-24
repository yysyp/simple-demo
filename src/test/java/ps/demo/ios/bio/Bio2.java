package ps.demo.ios.bio;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bio2 {

    public static void main(String[] args) throws Exception {
        int connectionNum = 0;
        int port = 8888;
        ExecutorService service = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(port);

        Socket socket = serverSocket.accept();
        service.execute(() -> {
            try {
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                while (true) {
                    String s = scanner.next().trim();
                    System.out.println("Receive msg from client, s=" + s);
                    printStream.println("BIO-ECHO:" + s);
                    if (s.equals("bye")) {
                        break;
                    }
                }
                System.out.println("server exit");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        System.out.println("To shutdown service");
        service.shutdown();
        System.out.println("To close socket");
        serverSocket.close();
    }
}
