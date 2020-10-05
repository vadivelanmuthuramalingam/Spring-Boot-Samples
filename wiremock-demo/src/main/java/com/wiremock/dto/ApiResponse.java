package com.wiremock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
	private String result = "Success";
	private String errorCode;
	private String errorMessage;
	private UserDetails userDetails;
}
