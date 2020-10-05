package com.pst.httpclient.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import com.pst.httpclient.builder.service.ApiResponse;
import com.pst.httpclient.builder.service.HttpClientTemplate;

import ch.qos.logback.core.joran.action.ActionUtil.Scope;

@Service
public class EmployeeService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier ("citistub")
	private HttpClientTemplate httpClient;
	
	@SuppressWarnings("unchecked")
	public void getReport()
	{
		httpClient.get("test Url", null, null, ApiResponse.class, null);
	}

}
