package pslab;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketConnectHttpsTest {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket();) {
            socket.connect(new InetSocketAddress("localhost", 443)); // Works for https as well.
            System.out.println("--->>Connect to https successfully!");
        } catch (Exception e) {
            System.out.println("--->>Connect to https failed!");
        }
    }

}
