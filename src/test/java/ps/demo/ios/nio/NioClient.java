package ps.demo.ios.nio;

import java.io.BufferedReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class NioClient {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8889));
            if (socketChannel.finishConnect()) {
                int i = 0;
                while(true) {
                    TimeUnit.SECONDS.sleep(1);
                    String info = "This is client i="+i+++", haha";
                    byteBuffer.clear();
                    byteBuffer.put(info.getBytes());
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        System.out.println("--->>client send to server: "+new String(byteBuffer.array()).trim());
                        socketChannel.write(byteBuffer);
                    }

                    byteBuffer.clear();
                    socketChannel.read(byteBuffer);
                    System.out.println("--->>client get info from server:"+new String(byteBuffer.array()).trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
