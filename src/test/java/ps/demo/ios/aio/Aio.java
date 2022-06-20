package ps.demo.ios.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class Aio {
    public static void main(String[] args) throws Exception {
        int port = 8890;
        AsynchronousServerSocketChannel ssc = AsynchronousServerSocketChannel.open();
        ssc.bind(new InetSocketAddress("localhost", port));
        ssc.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            void job(final AsynchronousSocketChannel sc) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                sc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        String str = new String(attachment.array()).trim();
                        ByteBuffer wrap = ByteBuffer.wrap(("AIO-PONG:" + str).getBytes());
                        sc.write(wrap, null, new CompletionHandler<Integer, Object>() {
                            @Override
                            public void completed(Integer result, Object attachment) {
                                job(sc);
                            }

                            @Override
                            public void failed(Throwable exc, Object attachment) {
                                System.out.println("error1");
                            }
                        });
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println("error2");
                    }
                });
            }

            @Override
            public void completed(AsynchronousSocketChannel sc, Object attachment) {
                ssc.accept(null, this);
                job(sc);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
                System.out.println("error3");
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
