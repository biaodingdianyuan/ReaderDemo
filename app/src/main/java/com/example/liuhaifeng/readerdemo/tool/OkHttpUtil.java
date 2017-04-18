package com.example.liuhaifeng.readerdemo.tool;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by liuhaifeng on 2017/4/18.
 */

public class OkHttpUtil {
    public static SSLContext getSafeFromServer(InputStream inputStream) {
        SSLContext sslContext = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("x.509");
            Certificate cert = certificateFactory.generateCertificate(inputStream);
            Log.d("cert key", ((X509Certificate) cert).getPublicKey().toString());

            //生成包含服务器证书的keyStore
            String keyStoreType = KeyStore.getDefaultType();
            Log.d("keystore type", keyStoreType);
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("cert", cert);

            //用含有服务器证书的keystore生成一个TrustManager
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            Log.d("tmfAlgorithm", tmfAlgorithm);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keyStore);

            //生成一个使用我们的TrustManager的SSLContext
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(),new SecureRandom());

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;
    }
}
