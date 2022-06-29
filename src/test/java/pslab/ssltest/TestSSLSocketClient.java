package pslab.ssltest;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class TestSSLSocketClient {
    private static String path = "D:\\patrick\\github-com\\simple-demo\\src\\main\\resources\\keys\\paiprivatekey-changeit.store";
    private static char[] password = "changeit".toCharArray();

    /**
     * @param args
     */
    public static void main(String[] args) {
        SSLContext context = null;
        try {
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(new FileInputStream(path), password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            context = SSLContext.getInstance("SSL");
            context.init(null, tm, null);
        } catch (Exception e) {         //省略捕获的异常信息
            e.printStackTrace();
        }
        SSLSocketFactory ssf = context.getSocketFactory();
        try {

            SSLSocket ss = (SSLSocket) ssf.createSocket("127.0.0.1", 8000);
            System.out.println("客户端就绪。");
            DataOutputStream outputStream = new DataOutputStream(ss.getOutputStream());
//	            outputStream.write("ww".getBytes());
            outputStream.writeUTF("22");
            System.out.println("=======");
            ObjectOutputStream os = new ObjectOutputStream(ss.getOutputStream());
            os.writeObject("echo : Hello");
//	            ObjectInputStream br = new ObjectInputStream(ss.getInputStream());
            System.out.println("客户端测试ok");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
