package com.pst.httpclient.builder.service;



import lombok.Getter;

//@Data
@Getter

public class ApiException extends RuntimeException
{

	
	private static final long serialVersionUID = 1L;
	
	public ApiException(String responseMessage, int responseCode) {
		super();
		this.responseMessage = responseMessage;
		this.responseCode = responseCode;
	}
	private String responseMessage;
	private int responseCode;
}
