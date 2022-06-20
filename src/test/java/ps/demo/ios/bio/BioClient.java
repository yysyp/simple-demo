package ps.demo.ios.bio;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class BioClient {

    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByAddress(new byte[] {127, 0, 0, 1});
        Socket socket = new Socket(address, 8888);

        try (BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream()); ) {

            String readLine = null;
            while (!"bye".equals(readLine)) {
                System.out.println("client: ");
                readLine = sysIn.readLine();
                socketOut.println(readLine);
                socketOut.flush();
                System.out.println("server: \n" + socketIn.readLine());
            }
        }

    }

}
