package com.pst.httpclient.builder.config.oauth;

import org.springframework.web.client.RestTemplate;

import com.pst.httpclient.builder.OAuthClientBuilder;
import com.pst.httpclient.builder.service.HttpClientTemplate;

public class OAuthClient 
{

	private HttpClientTemplate restCaller;
	private OAuthClientBuilder.Config config;
	private TokenDetails tokenDetails;
	
	public OAuthClient(HttpClientTemplate apiClient, OAuthClientBuilder.Config config) {
		super();
		this.restCaller = apiClient;
		this.config = config;
	}

	public OAuthClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TokenDetails getTokenDetails() {
		return tokenDetails;
	}
	
	
	public TokenDetails retriveToken()
	{
		restCaller.call();
		
		return new TokenDetails();
	}
	
	public TokenDetails refreshToken()
	{
		restCaller.call();
		
		return new TokenDetails();
	}
	
	
}
