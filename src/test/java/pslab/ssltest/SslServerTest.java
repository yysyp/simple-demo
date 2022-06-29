package pslab.ssltest;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;


public class SslServerTest {
    private static String path = "D:\\patrick\\github-com\\simple-demo\\src\\main\\resources\\keys\\paiprivatekey-changeit.store";
    private static char[] password = "changeit".toCharArray();

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        boolean flag = true;
        SSLContext context = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(path), password);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);
            KeyManager[] km = kmf.getKeyManagers();
            context = SSLContext.getInstance("SSL");
            context.init(km, null, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) context.getServerSocketFactory();
        try {
            SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(8000);
            System.out.println("等待客户点连接。。。");
            while (flag) {
                Socket s = ss.accept();
                System.out.println("接收到客户端连接");
                DataInputStream dis = new DataInputStream(s.getInputStream());
                dis.readUTF();
                System.out.println("-----");
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                os.writeObject("echo : Hello");
                os.flush();
                os.close();
                System.out.println();
                s.close();
            }
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
