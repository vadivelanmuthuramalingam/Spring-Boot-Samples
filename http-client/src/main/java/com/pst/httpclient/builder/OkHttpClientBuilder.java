package com.pst.httpclient.builder;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.pst.httpclient.builder.HttpClientServiceRegistry.HttpConnectionDetails;
import com.pst.httpclient.builder.Interceptor.LogInterceptorOkHttpClient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;


class OkHttpClientBuilder  implements ApiClientBuilder {

	@Override
	public Object build(HttpConnectionDetails httpConnectionDetails) {
		
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		
			        //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
		builder = builder.sslSocketFactory(sslSocketFactory(), x509TrustManager());
		builder = builder.retryOnConnectionFailure(false);
		builder = builder.connectionPool(pool());
		builder = builder.connectTimeout(httpConnectionDetails.getConnectTimeout(), TimeUnit.SECONDS);
		builder = builder.readTimeout(httpConnectionDetails.getConnectTimeout(), TimeUnit.SECONDS);
		builder = builder.writeTimeout(httpConnectionDetails.getConnectTimeout(),TimeUnit.SECONDS);
		builder = builder.addInterceptor(new LogInterceptorOkHttpClient());
			
		return builder.build();
    }

    
    private X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }


    private SSLSocketFactory sslSocketFactory() {
        try {
            //Trust any link
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a new connection pool with tuning parameters appropriate for a single-user application.
     * The tuning parameters in this pool are subject to change in future OkHttp releases. Currently
     */

    private ConnectionPool pool() {
        return new ConnectionPool(200, 5, TimeUnit.MINUTES);
    }
}
