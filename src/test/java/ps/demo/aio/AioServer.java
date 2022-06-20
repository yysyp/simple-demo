package ps.demo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class AioServer {

    public AsynchronousServerSocketChannel serverSocketChannel;

    public void listen() throws Exception {
        //打开一个服务端通道
        serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9988));//监听9988端口
        //监听
        serverSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AioServer>() {
            @Override
            public void completed(AsynchronousSocketChannel client, AioServer attachment) {
                try {
                    System.out.println("--->>server completion completed");

                    Executors.newFixedThreadPool(1).submit(new Callable<Object>() {
                        @Override
                        public Object call() {
                            try {
                                handleClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //当有新的客户端接入的时候，直接调用accept的方法，递归执行下去，这样可以保证多个客户端都可以阻塞
                    attachment.serverSocketChannel.accept(attachment, this);
                }
            }


            @Override
            public void failed(Throwable exc, AioServer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void handleClient(AsynchronousSocketChannel client) throws IOException {
        System.out.println("--->>server handleClient...");
        while (true) {
            System.out.println("--->>server Check read...");
            if (client.isOpen()) {
                System.out.println("--->>server 接收到新的客户端的连接，地址：" + client.getRemoteAddress());
                final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                //读取客户端发送的数据
                client.read(byteBuffer, client, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
                    @Override
                    public void completed(Integer result, AsynchronousSocketChannel attachment) {
                        try {
                            //读取请求，处理客户端发送的数据
                            byteBuffer.flip();
                            String content = Charset.defaultCharset().newDecoder().decode(byteBuffer).toString();
                            System.out.println("--->>server 服务端接受到客户端发来的数据：" + content);
                            //向客户端发送数据
//                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
//                            writeBuffer.put(("Server send echo [" + content + "]").getBytes());
//                            writeBuffer.flip();
//                            attachment.write(writeBuffer).get();
//                            System.out.println("--->>server write to client done");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
                        try {
                            System.out.println("--->>server completion failed.");
                            exc.printStackTrace();
                            attachment.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                System.out.println("--->>server after read");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new AioServer().listen();
        System.out.println("--->>server after listen");
        Thread.sleep(Integer.MAX_VALUE);
    }

}

