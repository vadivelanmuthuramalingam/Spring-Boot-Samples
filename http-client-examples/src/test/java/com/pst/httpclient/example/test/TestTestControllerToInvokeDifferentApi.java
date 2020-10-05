package com.pst.httpclient.example.test;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pst.httpclient.example.api.TestControllerToInvokeDifferentApi;

public class TestTestControllerToInvokeDifferentApi extends ApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		//mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.mockMvc = MockMvcBuilders.standaloneSetup(new TestControllerToInvokeDifferentApi()).build();
	}

	@Test
	public void testEmployee() throws Exception {
		
		try
		{
			ResultActions resultActions = mockMvc.perform(get("/test")).andExpect(status().isOk());
			
			resultActions.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(jsonPath("$.empId").value("100"))
					.andExpect(jsonPath("$.empName").value("EmpName Test"))
					;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		ResultActions resultActions = mockMvc.perform(get("/test")).andExpect(status().isOk());
		
		resultActions.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.empId").value("100"))
				.andExpect(jsonPath("$.empName").value("EmpName Test"))
				;

	}

}