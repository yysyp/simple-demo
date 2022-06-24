package ps.demo.ios.aio;

import ps.demo.util.MyFileUtil;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class Example3 {

    static Thread current;
    static final Path path = MyFileUtil.getFileInHomeDir("aiotest.txt").toPath();

    public static void main(String[] args) {

        CompletionHandler<Integer, ByteBuffer> handler =
                new CompletionHandler<Integer, ByteBuffer>() {

                    String encoding = System.getProperty("file.encoding");

                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        System.out.println("Read bytes: " + result);
                        attachment.flip();
                        System.out.print(Charset.forName(encoding).decode(attachment));
                        attachment.clear();
                        current.interrupt();
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println(attachment);
                        System.out.println("Error:" + exc);
                        current.interrupt();
                    }
                };

        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path,
                StandardOpenOption.READ)) {

            current = Thread.currentThread();
            ByteBuffer buffer = ByteBuffer.allocate(100);
            asynchronousFileChannel.read(buffer, 0, buffer, handler);

            System.out.println("Waiting for reading operation to end …\n");
            try {
                current.join();
            } catch (InterruptedException e) {
            }

            //the buffer was passed as attachment
            System.out.println("\n\nClosing everything and leave! Bye, bye …");

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
