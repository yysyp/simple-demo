package ps.demo.util;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class MyKeyStoreUtil {

    public static KeyStore getKeyStoreFromCer(File cerFile, String alias) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(null);//Make an empty store

        try (InputStream fis = new FileInputStream(cerFile);
             BufferedInputStream bis = new BufferedInputStream(fis);) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            while (bis.available() > 0) {
                Certificate cert = cf.generateCertificate(bis);
                trustStore.setCertificateEntry(alias + bis.available(), cert);
            }
        }
        return trustStore;
    }

    public static KeyStore getKeyStore(File ksFile, String password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream in = new FileInputStream(ksFile)) {
            if (password == null) {
                keystore.load(in, null);
            } else {
                keystore.load(in, password.toCharArray());
            }
        }
        return keystore;
    }


}
