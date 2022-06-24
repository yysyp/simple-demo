package ps.demo.ios.aio;

import ps.demo.util.MyFileUtil;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class Example2 {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.wrap("The qrqrql qkwl qw3s1 kloqprq".getBytes());

        //Path path = Paths.get("folder", "test.txt");
        Path path = MyFileUtil.getFileInHomeDir("aiotest.txt").toPath();
        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path,
                StandardOpenOption.WRITE)) {

            Future<Integer> result = asynchronousFileChannel.write(buffer, 100);

            while (!result.isDone()) {
                System.out.println("Do something else while writing …");
            }

            System.out.println("Written done: " + result.isDone());
            System.out.println("Bytes written: " + result.get());

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
