package pslab;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class ByteBufferTest {

    public static void main(String[] args) {
        byte[] bytes = "hello world".getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);

        ByteBuffer byteBuffer2 = ByteBuffer.wrap(bytes);

        System.out.println("byteBuffer2" + new String(byteBuffer2.array()));
    }
}
