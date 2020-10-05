package com.pst.httpclient.builder;

import com.pst.httpclient.builder.HttpClientServiceRegistry.HttpConnectionDetails;

public interface ApiClientBuilder {
	public Object build(HttpConnectionDetails httpConnectionDetails) ;
}
