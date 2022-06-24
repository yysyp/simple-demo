package ps.demo.ios.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Nio {
    static boolean stop = false;

    public static void main(String[] args) throws Exception {
        int connectionNum = 0;
        int port = 8889;
        ExecutorService service = Executors.newCachedThreadPool();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress("localhost", port));
        Selector selector = Selector.open();
        ssc.register(selector, ssc.validOps());
        while (!stop) {
            if (10 == connectionNum) {
                stop = true;
            }
            int num = selector.select();
            if (num == 0) {
                continue;
            }
            Iterator<SelectionKey> events = selector.selectedKeys().iterator();
            while (events.hasNext()) {
                SelectionKey event = events.next();

                if (event.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    connectionNum++;
                } else if (event.isReadable()) {
                    try {
                        SocketChannel sc = (SocketChannel) event.channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int size = sc.read(buf);
                        if (-1 == size) {
                            sc.close();
                        }
                        String result = new String(buf.array()).trim();
                        System.out.println("--->>server get info from client: "+ result);
                        ByteBuffer wrap = ByteBuffer.wrap(("NIO-ECHO:" + result).getBytes());
                        sc.write(wrap);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (event.isWritable()) {
                    SocketChannel sc = (SocketChannel) event.channel();
                }

                events.remove();
            }
        }
        service.shutdown();
        ssc.close();
    }
}
