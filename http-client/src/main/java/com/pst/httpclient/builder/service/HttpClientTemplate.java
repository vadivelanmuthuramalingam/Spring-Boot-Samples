package com.pst.httpclient.builder.service;

import java.net.URI;
import java.util.Map;

//import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class HttpClientTemplate extends HttpClientConnector {

	//private RestTemplate restTemplate;

	public HttpClientTemplate(Object httpClient) {
		super(httpClient);
		//this.restTemplate = restTemplate;
	}

	public void call() {
		System.out.println("test");
	}

	public <T extends Object> ApiResponse<T> get(URI uri, Class<T> responseType, HttpHeaders headers) {
		HttpEntity<?> requestEntity = this.createHttpEntity(headers);

		return super.call(uri, requestEntity, responseType, HttpMethod.GET);
	}

	public <T extends Object> ApiResponse<T> get(String url, Map<String, String> requestParams, Map<String, String> pathVariables, Class<T> responseType, HttpHeaders headers) {
		
		URI uri = createUri(url, requestParams, pathVariables);
		HttpEntity<?> requestEntity = this.createHttpEntity(headers);

		return super.call(uri, requestEntity, responseType, HttpMethod.GET);
	}

	public <T extends Object> ApiResponse<T> postEntity(URI uri, T requestObject, Class<T> responseType, 
			HttpHeaders headers)
	{
		HttpEntity<?> requestEntity = this.createHttpEntity(requestObject, headers);
		return super.call(uri, requestEntity, responseType, HttpMethod.POST);
	}

	public <T extends Object> ApiResponse<T> putEntity(URI uri, T requestObject, Class<T> responseType, HttpHeaders headers) {
		HttpEntity<?> requestEntity = this.createHttpEntity(headers);

		return super.call(uri, requestEntity, responseType, HttpMethod.PUT);
	
	}

	
	private <T extends Object> HttpEntity<T> createHttpEntity(T requestObject, HttpHeaders headers) {
		return new HttpEntity<T>(requestObject, headers);
	}

	private <T extends Object> HttpEntity<?> createHttpEntity(HttpHeaders headers) {
		return new HttpEntity<T>(headers);
	}

	public static URI createUri(String url, Map<String, String> requestParams, Map<String, String> pathVariables) 
	{
		URI uri = null;
		try
		{
			uri = new URI(url);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		

		return uri;

	}

}
