package com.wiremock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wiremock.dto.ApiResponse;
import com.wiremock.dto.UserDetails;
import com.wiremock.util.BaseRestOutboundProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CrudController {

	@Autowired
	BaseRestOutboundProcessor baseRestOutboundProcessor;

	@Value("${third.party.base.url}")
	String baseUrl;

	@PostMapping("/user")
	public ResponseEntity<ApiResponse> saveUserDetails(@RequestBody UserDetails request) {
		Map<String, String> headers = new HashMap<>();
		headers.put("access-key", UUID.randomUUID()
		                              .toString());
		headers.put("secret-key", UUID.randomUUID()
		                              .toString());
		ResponseEntity<ApiResponse> responseEntity = baseRestOutboundProcessor.post(baseUrl + "api/user", request, ApiResponse.class,
		                                                                              headers);
		return ResponseEntity.ok(responseEntity != null ? responseEntity.getBody() : null);
	}

	@PutMapping("/user")
	public ResponseEntity<ApiResponse> saveUserData(@RequestBody UserDetails request) {
		Map<String, String> headers = new HashMap<>();
		headers.put("access-key", UUID.randomUUID()
		                              .toString());
		headers.put("secret-key", UUID.randomUUID()
		                              .toString());
		ResponseEntity<ApiResponse> responseEntity = baseRestOutboundProcessor.put(baseUrl + "/api/user", request, ApiResponse.class,
		                                                                             headers);
		return ResponseEntity.ok(responseEntity != null ? responseEntity.getBody() : null);
	}

	@GetMapping("/user")
	public ResponseEntity<ApiResponse> getUserData(@RequestBody UserDetails request) {
		Map<String, String> headers = new HashMap<>();
		headers.put("access-key", UUID.randomUUID()
		                              .toString());
		headers.put("secret-key", UUID.randomUUID()
		                              .toString());
		ResponseEntity<ApiResponse> responseEntity = baseRestOutboundProcessor.get(baseUrl + "/api/user", request, ApiResponse.class,
		                                                                             headers);
		return ResponseEntity.ok(responseEntity != null ? responseEntity.getBody() : null);
	}

	@DeleteMapping("/user")
	public ResponseEntity<ApiResponse> deleteUser(@RequestParam("id") String id) {
		Map<String, String> headers = new HashMap<>();
		headers.put("access-key", UUID.randomUUID()
		                              .toString());
		headers.put("secret-key", UUID.randomUUID()
		                              .toString());
		ResponseEntity<ApiResponse> responseEntity = baseRestOutboundProcessor.delete(baseUrl + "api/user?id=" + id, null, ApiResponse.class,
		                                                                               headers);
		return ResponseEntity.ok(responseEntity != null ? responseEntity.getBody() : null);
	}
}
