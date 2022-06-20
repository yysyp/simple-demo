package ps.demo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.*;

public class AioClient {

    public static void main(String[] args) throws Exception {
        //打开一个客户端通道
        AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
        //与服务端建立连接
        channel.connect(new InetSocketAddress("127.0.0.1", 9988));
        //睡眠一秒，等待与服务端的连接
        Thread.sleep(1000);

        Future w = Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                writeToSocket(channel);
            }
        });

        Future r = Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                readFromSocket(channel);
                return null;
            }
        });

        w.get();
        r.get();
    }

    private static void writeToSocket(AsynchronousSocketChannel channel) {
        try {
            int num = 0;
            for (int i = 0; i < 10; i++) {
                num = new Random().nextInt(1000)+1;
                //向服务端发送数据
                System.out.println("##client to sent msg: i="+i+", num="+num);
                channel.write(ByteBuffer.wrap(("Hello,我是客户端,i="+i+", num="+num).getBytes())).get();
                Thread.sleep(num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readFromSocket(AsynchronousSocketChannel channel) {
        try {
            while (true) {
                //从服务端读取返回的数据
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                channel.read(byteBuffer).get();//将通道中的数据写入缓冲Buffer
                byteBuffer.flip();
                String result = Charset.defaultCharset().newDecoder().decode(byteBuffer).toString();
                System.out.println("##client 客户端收到服务端返回的内容：" + result);//服务端返回的数据
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
