package com.pst.httpclient.builder;

import com.pst.httpclient.builder.HttpClientServiceRegistry.HttpConnectionDetails;

class ApiClientConnectionFactory 
{
	public static ApiClientBuilder createHttpConnectionTemplate(HttpConnectionDetails httpConnectionDetails)
	{
		
		if(httpConnectionDetails.getConnType().equals("APACHE"))
			return new PoolHttpConnectionBuilder();
		if(httpConnectionDetails.getConnType().equals("OKHTTP"))
			return new OkHttpClientBuilder();
		else
			return new SimpleHttpClientBuilder();
	
		
	}
}
