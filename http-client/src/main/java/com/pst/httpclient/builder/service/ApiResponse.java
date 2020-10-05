package com.pst.httpclient.builder.service;

import org.springframework.http.HttpHeaders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public final class ApiResponse<T> {
	
	
	private boolean success;
	private int httpStatusCode;
	private HttpHeaders httpHeaders;
	private T responseData;
	private ApiException apiException;

}
