package pslab;

import ps.demo.util.MyFileUtil;
import ps.demo.util.MyKeyStoreUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.SecureRandom;

public class TlsConnection {

    public static void main(String[] args) throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(MyKeyStoreUtil.getKeyStoreFromCer(MyFileUtil.getFileInHomeDir("yysyp.cer"), "yysyp"));

        sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        try (
        SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 8443);
        InputStream is = new BufferedInputStream(socket.getInputStream());
        OutputStream os = new BufferedOutputStream(socket.getOutputStream());
        ) {
            os.write("hello".getBytes());
            os.flush();
            byte[] data = new byte[2048];
            int len = is.read(data);
            if (len <= 0) {
                throw new IOException("no data received");
            }
            System.out.printf("client received %d bytes: %s%n",
                    len, new String(data, 0, len));
        }

    }
}
