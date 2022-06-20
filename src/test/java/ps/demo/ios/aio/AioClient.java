package ps.demo.ios.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AioClient {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
        Future<Void> future = channel.connect(new InetSocketAddress("127.0.0.1", 8890));
        future.get(3, TimeUnit.SECONDS);


        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        ByteBuffer byteBuffer = null;//ByteBuffer.allocate(1024);
        for (int i = 0; i < 10; i++) {
            byteBuffer = ByteBuffer.wrap(("byte buffer"+i).getBytes(StandardCharsets.UTF_8));
            channel.write(byteBuffer).get();
        }
        byteBuffer = ByteBuffer.allocate(1024);
        channel.read(byteBuffer).get();

        byteBuffer.flip();
//        byte[] bytes = new byte[byteBuffer.remaining()];
//        byteBuffer.get(bytes);
//        String newContent = new String(bytes, Charset.defaultCharset());
        String result = Charset.defaultCharset().newDecoder().decode(byteBuffer).toString();
        System.out.println("##client: "+result);

        channel.close();
    }

}
