package com.wiremock.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wiremock.dto.ApiResponse;
import com.wiremock.dto.UserDetails;
import com.wiremock.util.BaseRestOutboundProcessor;

@Service
public class ServiceCall {

	@Value("${third.party.base.url}")
	String baseUrl;
	
	@Autowired
	private BaseRestOutboundProcessor baseRestOutboundProcessor;
	
	public ResponseEntity<ApiResponse> test()
	{
		Map<String, String> headers = new HashMap<>();
		
		ResponseEntity<ApiResponse> responseEntity = baseRestOutboundProcessor.get(baseUrl + "api/getuserdetails/1", null, ApiResponse.class,
                headers);
		
		return responseEntity;
	}
}
