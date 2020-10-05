package com.pst.httpclient.builder.service;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientConnector {

	private Object httpClient;

	public HttpClientConnector(Object httpClient) {
		super();
		this.httpClient = httpClient;
	}
	
	
	protected <T extends Object> ApiResponse<T> call(URI uri, HttpEntity<?> requestEntity, Class<T> responseType, HttpMethod httpMethod)
	{
		
		if(httpClient instanceof RestTemplate)
			return callByRestTemplate(uri, requestEntity, responseType, httpMethod);
		else
			return null;
		
		
	}
	
	private <T extends Object> ApiResponse<T> callByRestTemplate(URI uri, HttpEntity<?> requestEntity, Class<T> responseType, HttpMethod httpMethod)
	{
		ApiResponse<T> apiResponse = null;
		ResponseEntity<T> responseEntity =	null;
		RestTemplate restTemplate = null;
		try
		{
			restTemplate = (RestTemplate) this.httpClient;
			responseEntity = restTemplate.exchange(uri, httpMethod, requestEntity, responseType);
			
			apiResponse = new ApiResponse<T>(true, responseEntity.getStatusCodeValue(), 
					responseEntity.getHeaders(), responseEntity.getBody(), null);
		}
		catch(ApiException apiException)
		{
			apiResponse = new ApiResponse<T>(false, apiException.getResponseCode(), null, null, apiException);
		}
		catch(Exception ex)
		{
			apiResponse = new ApiResponse<T>(false, 999, null, null, new ApiException(ex.getMessage(), 999));
		}
		return apiResponse;
		
	}
	
	private String get(String url, Map<String, String> header) throws IOException {
        Headers headerbuild = Headers.of(header);
        Request request = new Request.Builder().url(url).headers(headerbuild).
                        build();
        OkHttpClient client = (OkHttpClient) httpClient;
        
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
