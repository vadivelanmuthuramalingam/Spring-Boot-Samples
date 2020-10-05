package com.pst.httpclient.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.pst.httpclient.builder.HttpClientServiceRegistry.HttpConnectionDetails;
import com.pst.httpclient.builder.Interceptor.HttpErrorHandler;
import com.pst.httpclient.builder.Interceptor.LoggingInterceptor;
import com.pst.httpclient.builder.Interceptor.OAuthInterceptor;

import org.apache.http.conn.ssl.TrustStrategy;

class PoolHttpConnectionBuilder  implements ApiClientBuilder {

	@Override
	public Object build(HttpConnectionDetails httpConnectionDetails) {
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory(httpConnectionDetails));
		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new OAuthInterceptor());
		interceptors.add(new LoggingInterceptor());
		restTemplate.setInterceptors (interceptors);
		
		restTemplate.setErrorHandler(new HttpErrorHandler());
		
		return restTemplate;
	}

	private ClientHttpRequestFactory httpRequestFactory(HttpConnectionDetails httpConnectionDetails) {
		return new HttpComponentsClientHttpRequestFactory(createCloseableHttpClient(httpConnectionDetails));
	}

	// Valid one but trying CloseableHttpClient
	/*
	 * private HttpClient httpClient(HttpConnectionDetails httpConnectionDetails) {
	 * Registry<ConnectionSocketFactory> registry =
	 * RegistryBuilder.<ConnectionSocketFactory>create() .register("http",
	 * PlainConnectionSocketFactory.getSocketFactory()) .register("https",
	 * SSLConnectionSocketFactory.getSocketFactory()) .build();
	 * 
	 * PoolingHttpClientConnectionManager connectionManager =
	 * createPoolingHttpClientConnectionManager(registry, httpConnectionDetails);
	 * RequestConfig requestConfig = createRequestConfig(httpConnectionDetails);
	 * 
	 * 
	 * return HttpClientBuilder.create() .setDefaultRequestConfig(requestConfig)
	 * .setConnectionManager(connectionManager) .build(); }
	 */

	private CloseableHttpClient createCloseableHttpClient(HttpConnectionDetails httpConnectionDetails) {
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();

		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = createPoolingHttpClientConnectionManager(registry,
				httpConnectionDetails);
		
		RequestConfig requestConfig = createRequestConfig(httpConnectionDetails);
		
		SSLConnectionSocketFactory sslConnectionSocketFactory = null;
		try 
		{
			sslConnectionSocketFactory = createSSLConnectionSocketFactory(httpConnectionDetails);
		} 
		catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException
				| CertificateException | IOException e) {
			e.printStackTrace();
		}

		return createHttpClient(poolingHttpClientConnectionManager, requestConfig, sslConnectionSocketFactory);
	}

	private CloseableHttpClient createHttpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
			RequestConfig requestConfig, SSLConnectionSocketFactory sslConnectionSocketFactory) {
		
		/*CloseableHttpClient result = HttpClientBuilder
				.create()
				.setConnectionManager(poolingHttpClientConnectionManager)
				.setDefaultRequestConfig(requestConfig).build();
		*/
		
		CloseableHttpClient result = null;
		
		if(sslConnectionSocketFactory == null)
		{
			result = HttpClients.custom()
					.disableAutomaticRetries()
					.disableAuthCaching()
					.disableRedirectHandling()
					.setConnectionManager(poolingHttpClientConnectionManager)
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.setDefaultRequestConfig(requestConfig)
					.setKeepAliveStrategy(connectionKeepAliveStrategy())
					.build();
		}
		else
		{
			result = HttpClients.custom()
					.disableAutomaticRetries()
					.disableAuthCaching()
					.disableRedirectHandling()
					.setConnectionManager(poolingHttpClientConnectionManager)
					.setSSLHostnameVerifier(new NoopHostnameVerifier())
					.setDefaultRequestConfig(requestConfig)
					.setSSLSocketFactory(sslConnectionSocketFactory)
					.build();
		}
			
		return result;
	}

	private ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
	    return (httpResponse, httpContext) -> {
	        HeaderIterator headerIterator = httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE);
	        HeaderElementIterator elementIterator = new BasicHeaderElementIterator(headerIterator);

	        while (elementIterator.hasNext()) {
	          HeaderElement element = elementIterator.nextElement();
	          String param = element.getName();
	          String value = element.getValue();
	          if (value != null && param.equalsIgnoreCase("timeout")) {
	            return Long.parseLong(value);// * 1000; // convert to ms
	          }
	        }

	        return 3000;//DEFAULT_KEEP_ALIVE_TIME;
	      };
	    }
	private PoolingHttpClientConnectionManager createPoolingHttpClientConnectionManager(
			Registry<ConnectionSocketFactory> registry, HttpConnectionDetails httpConnectionDetails) {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
		
		if(httpConnectionDetails.getMaxTotal() != null && httpConnectionDetails.getMaxTotal() > 0)
			connectionManager.setMaxTotal(httpConnectionDetails.getMaxTotal());
		
		if(httpConnectionDetails.getDefaultMaxPerRoute() != null && httpConnectionDetails.getDefaultMaxPerRoute() > 0)
			connectionManager.setDefaultMaxPerRoute(httpConnectionDetails.getDefaultMaxPerRoute());
		
		if(httpConnectionDetails.getValidateAfterInactivity() != null && httpConnectionDetails.getValidateAfterInactivity() > 0)
				connectionManager.setValidateAfterInactivity(httpConnectionDetails.getValidateAfterInactivity());
		
		
		return connectionManager;
	}

	private RequestConfig createRequestConfig(HttpConnectionDetails httpConnectionDetails) {
		RequestConfig requestConfig = RequestConfig.custom()
				// The time for the server to return data (response) exceeds the throw of read
				// timeout
				.setSocketTimeout(httpConnectionDetails.getSocketTimeout())
				// The time to connect to the server (handshake succeeded) exceeds the throw
				// connect timeout
				.setConnectTimeout(httpConnectionDetails.getConnectTimeout())
				// The timeout to get the connection from the connection pool. If the connection
				// is not available after the timeout, the following exception will be thrown
				// org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for
				// connection from pool
				.setConnectionRequestTimeout(httpConnectionDetails.getConnectionRequestTimeout()).build();

		return requestConfig;
	}
	
	private SSLConnectionSocketFactory createSSLConnectionSocketFactory(HttpConnectionDetails httpConnectionDetails) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException
	{
		/*
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(new FileInputStream(new File(httpConnectionDetails.getKeystorePath())),
				httpConnectionDetails.getSecrets().toCharArray());

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
		  new SSLContextBuilder()
		    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
		    .loadKeyMaterial(keyStore, httpConnectionDetails.getSecrets().toCharArray())
		    .build(),
		    NoopHostnameVerifier.INSTANCE);
		
		return socketFactory;
		*/
		
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
	        @Override
	        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
	            return true;
	        }
	    };
	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    
	    return csf;
	 
	}
}
