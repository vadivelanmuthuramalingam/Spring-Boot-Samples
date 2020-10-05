package com.wiremock.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.wiremock.controller.CrudController;
import com.wiremock.dto.ApiResponse;
import com.wiremock.dto.UserDetails;
import com.wiremock.service.ServiceCall;
import com.wiremock.util.CustomObjectMapper;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestCrudController {

	@ClassRule
	public static WireMockRule wireMockRule = new WireMockRule(8900);

	@Autowired
	CrudController crudController;
	
	@Autowired
	private  ServiceCall serviceCall;

	@Value("${third.party.base.url}")
	private String url;

	ObjectMapper objectMapper = new CustomObjectMapper();

	@Before
	public void init() {
		ReflectionTestUtils.setField(crudController, "baseUrl", url);
		wireMockRule.resetMappings();
		wireMockRule.resetScenarios();
		wireMockRule.resetRequests();
		}

	@Test
	public void testSaveUserDetails() throws Exception {
		//UserDetails expectedResponse = generateUserDetails();
		ApiResponse apiResponse = generateApiResponse();
		
		String expectedResponseString = objectMapper.writeValueAsString(apiResponse);
		wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/api/user"))
		                             .willReturn(WireMock.aResponse()
		                                                 .withBody(expectedResponseString)
		                                                 .withStatus(HttpStatus.OK.value())
		                                                 .withHeader("Content-Type",
		                                                             "application/json;charset=UTF-8")));
		ResponseEntity<ApiResponse> responseEntity = crudController.saveUserDetails(null);
		assertNotNull(responseEntity);
		assertTrue(responseEntity.getBody().getUserDetails() instanceof UserDetails);
		//UserDetails actualResponse = (UserDetails) responseEntity.getBody().getUserDetails();
		ApiResponse actualApiResponse = responseEntity.getBody();
		assertNotNull(actualApiResponse);
		assertUserDetails(apiResponse, actualApiResponse);
	}

	@Test
	public void testSaveUserDetails_Error() throws Exception {
		wireMockRule.stubFor(WireMock.post(WireMock.urlMatching("/api/user"))
		                             .willReturn(WireMock.aResponse()
		                                                 .withStatus(HttpStatus.BAD_REQUEST.value())

		                                                 .withHeader("Content-Type",
		                                                             "application/json;charset=UTF-8")));
		try {
			ResponseEntity<ApiResponse> responseEntity = crudController.saveUserDetails(null);
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
		}
	}

	@Test
	public void testSaveUserData() throws Exception {
		//UserDetails expectedResponse = generateUserDetails();
		ApiResponse apiResponse = generateApiResponse();
		String expectedResponseString = objectMapper.writeValueAsString(apiResponse);
		wireMockRule.stubFor(WireMock.put(WireMock.urlMatching("/api/user"))
		                             .willReturn(WireMock.aResponse()
		                                                 .withBody(expectedResponseString)
		                                                 .withStatus(HttpStatus.OK.value())
		                                                 .withHeader("Content-Type",
		                                                             "application/json;charset=UTF-8")));
		ResponseEntity<ApiResponse> responseEntity = crudController.saveUserData(null);
		assertNotNull(responseEntity);
		assertTrue(responseEntity.getBody().getUserDetails() instanceof UserDetails);
		ApiResponse actualApiResponse = responseEntity.getBody();
		assertNotNull(responseEntity.getBody().getUserDetails());
		assertUserDetails(apiResponse, actualApiResponse);
	}

	@Test
	public void testGetUserData() throws Exception {
		UserDetails expectedResponse = generateUserDetails();
		String expectedResponseString = objectMapper.writeValueAsString(expectedResponse);
		wireMockRule.stubFor(WireMock.get(WireMock.urlMatching("/api/user"))
		                             .willReturn(WireMock.aResponse()
		                                                 .withBody(expectedResponseString)
		                                                 .withStatus(HttpStatus.OK.value())
		                                                 .withHeader("Content-Type",
		                                                             "application/json;charset=UTF-8")));
		ResponseEntity<ApiResponse> responseEntity = crudController.getUserData(null);
		assertNotNull(responseEntity);
		assertTrue(responseEntity.getBody() instanceof ApiResponse);
		UserDetails actualResponse = (UserDetails) responseEntity.getBody().getUserDetails();
		assertNotNull(actualResponse);
		//assertUserDetails(expectedResponse, actualResponse);
		
		
		wireMockRule.stubFor(WireMock.get(WireMock.urlMatching("/api/getuserdetails/1"))
                .willReturn(WireMock.aResponse()
                                    .withBody(expectedResponseString)
                                    .withStatus(HttpStatus.OK.value())
                                    .withHeader("Content-Type",
                                                "application/json;charset=UTF-8")));

		ResponseEntity<ApiResponse> responseEntity1  = serviceCall.test();
		assertNotNull(responseEntity1);
		
	}

	@Test
	public void testDeleteUser() throws Exception {
		ApiResponse expectedResponse = new ApiResponse();
		String expectedResponseString = objectMapper.writeValueAsString(expectedResponse);
		String id = UUID.randomUUID()
		                .toString();
		wireMockRule.stubFor(WireMock.delete(WireMock.urlPathEqualTo("/api/user?id=" + id))
		                             .willReturn(WireMock.aResponse()
		                                                 .withBody(expectedResponseString)
		                                                 .withStatus(HttpStatus.OK.value())
		                                                 .withHeader("Content-Type",
		                                                             "application/json;charset=UTF-8")));
		ResponseEntity<ApiResponse> responseEntity = crudController.deleteUser(id);
		assertNotNull(responseEntity);
		assertTrue(responseEntity.getBody() instanceof ApiResponse);
		ApiResponse actualResponse = (ApiResponse) responseEntity.getBody();
		assertNotNull(actualResponse);
		assertEquals(expectedResponse.getResult(), actualResponse.getResult());
	}

	@Test
	public void testServiceCall() throws Exception
	{
		
		ApiResponse expectedResponse = new ApiResponse();
		String expectedResponseString = objectMapper.writeValueAsString(expectedResponse);
		String id = UUID.randomUUID()
		                .toString();
		wireMockRule.stubFor(WireMock.get(WireMock.urlMatching("/api/getuserdetails/1"))
                .willReturn(WireMock.aResponse()
                                    .withBody(expectedResponseString)
                                    .withStatus(HttpStatus.OK.value())
                                    .withHeader("Content-Type",
                                                "application/json;charset=UTF-8")));

		ResponseEntity<ApiResponse> responseEntity  = serviceCall.test();
		assertNotNull(responseEntity);
		assertTrue(responseEntity.getBody() instanceof ApiResponse);
		ApiResponse actualResponse = (ApiResponse) responseEntity.getBody();
		assertNotNull(actualResponse);
		assertEquals(expectedResponse.getResult(), actualResponse.getResult());
		
	}
	private UserDetails generateUserDetails() {
		UserDetails userDetails = new UserDetails();
		userDetails.setCity(RandomStringUtils.randomAlphabetic(10));
		userDetails.setCountry(RandomStringUtils.randomAlphabetic(10));
		userDetails.setDateOfBirth(LocalDate.now());
		userDetails.setFirstName(RandomStringUtils.randomAlphabetic(10));
		userDetails.setLastName(RandomStringUtils.randomAlphabetic(10));
		userDetails.setId(UUID.randomUUID()
		                  .toString());
		return userDetails;
	}
	
	private ApiResponse generateApiResponse() {
		UserDetails userDetails = new UserDetails();
		userDetails.setCity(RandomStringUtils.randomAlphabetic(10));
		userDetails.setCountry(RandomStringUtils.randomAlphabetic(10));
		userDetails.setDateOfBirth(LocalDate.now());
		userDetails.setFirstName(RandomStringUtils.randomAlphabetic(10));
		userDetails.setLastName(RandomStringUtils.randomAlphabetic(10));
		userDetails.setId(UUID.randomUUID()
		                  .toString());
		
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setErrorCode("");
		apiResponse.setResult("");
		apiResponse.setUserDetails(userDetails);
		
		return apiResponse;
	}

	private void assertUserDetails(ApiResponse expected, ApiResponse  actual) {
		assertEquals(expected.getUserDetails().getCity(), actual.getUserDetails().getCity());
		assertEquals(expected.getUserDetails().getFirstName(), actual.getUserDetails().getFirstName());
		assertEquals(expected.getUserDetails().getLastName(), actual.getUserDetails().getLastName());
		assertEquals(expected.getUserDetails().getCountry(), actual.getUserDetails().getCountry());
		assertEquals(expected.getUserDetails().getId(), actual.getUserDetails().getId());
		assertEquals(expected.getUserDetails().getDateOfBirth(), actual.getUserDetails().getDateOfBirth());
		//assertEquals(expected.getResult(), actual.getResult());
	}
}
