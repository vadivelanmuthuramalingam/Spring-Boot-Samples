package com.pst.httpclient.builder;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.pst.httpclient.builder.HttpClientServiceRegistry.HttpConnectionDetails;
import com.pst.httpclient.builder.Interceptor.HttpErrorHandler;
import com.pst.httpclient.builder.Interceptor.LoggingInterceptor;
import com.pst.httpclient.builder.Interceptor.OAuthInterceptor;

class SimpleHttpClientBuilder implements ApiClientBuilder {

	@Override
	public Object build(HttpConnectionDetails httpConnectionDetails) {
		RestTemplate restTemplate = new RestTemplate(createHttpRequestFactory(httpConnectionDetails));
		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new OAuthInterceptor());
		interceptors.add(new LoggingInterceptor());
		restTemplate.setInterceptors (interceptors);
		
		restTemplate.setErrorHandler(new HttpErrorHandler());
		
		
		return restTemplate;
	}
	private ClientHttpRequestFactory createHttpRequestFactory(HttpConnectionDetails httpConnectionDetails) {
		 SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		 simpleClientHttpRequestFactory.setConnectTimeout(httpConnectionDetails.getConnectTimeout());
		 simpleClientHttpRequestFactory.setReadTimeout(httpConnectionDetails.getConnectionRequestTimeout());

		 /* enable the below code if Proxy is required
		 InetSocketAddress address = new InetSocketAddress("userproxy.glb.ebc.local", 8080);
		    Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
		    simpleClientHttpRequestFactory.setProxy(proxy);
		 */
		 return simpleClientHttpRequestFactory;
		}

	
}
