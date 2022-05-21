
package com.pod4authenticationservice.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pod4authenticationservice.payload.LoginRequest;
import com.pod4authenticationservice.security.jwt.JwtUtils;

/**
 * 
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
	/**
	 * 
	 */
	@Autowired
	public transient MockMvc mockMvc;

	/**
	 * 
	 */
	@MockBean
	private transient JwtUtils jwtUtils;
	
	/**
	 * @throws Exception
	 * 
	 * This Test is use for Authentication Testing
	 */
	/**
	 * @throws Exception
	 */
	@Test
	public void testAuthenticateUser() throws Exception {

		final ObjectMapper mapper = new ObjectMapper();

		final LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword("12345");
		loginRequest.setUserName("satyam1");

		// Java object to JSON string
		final String jsonString = mapper.writeValueAsString(loginRequest);

		final MvcResult result = mockMvc
				.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andReturn();

		assertEquals("{\"userId\":1,\"token\":null,\"username\":\"satyam1\",\"roles\":[\"ROLE_USER\"]}",
				result.getResponse().getContentAsString());
	}
	
	
	
	
	/**
	 * @throws Exception
	 * 
	 * Testing AuthenticateUser Rest Point When UserName And Password Is Blank
	 */
	@Test
	public void testAuthenticateUserRestPointWhenUserNameAndPasswordIsBlank() throws Exception {

		final ObjectMapper mapper = new ObjectMapper();

		final LoginRequest loginRequest = new LoginRequest();
			loginRequest.setPassword("");
			loginRequest.setUserName("");

		// Java object to JSON string
		final String jsonString = mapper.writeValueAsString(loginRequest);

		 mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
						.content(jsonString)).andExpect(status().isBadRequest());
	}
	
	

	/**
	 * @throws Exception
	 * 
	 * This test is use for Validation testing user having Valid token
	 */
	@Test
	public void testValidateAndReturnUser() throws Exception {

		
		final ResponseEntity<String> authorize=new ResponseEntity<>("Validated Successfully....",HttpStatus.OK);
		
		when(jwtUtils.validateJwtToken(Mockito.anyString())).thenReturn(authorize);

		final MvcResult result = mockMvc.perform(post("/api/auth/validate").header("Authorization", "Bearer " + "satyam")
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		assertEquals("Validated Successfully....", result.getResponse().getContentAsString());
	}


/**
 * @throws Exception
 * 
 * This test is use for Validation testing user having not Valid token
 */
@Test
public void testValidateAndReturnUserHavingNotValidToken() throws Exception {

	
	final ResponseEntity<String> authorize=new ResponseEntity<>("JWT Token is Not Valid.*",HttpStatus.UNAUTHORIZED);
	
	when(jwtUtils.validateJwtToken(Mockito.anyString())).thenReturn(authorize);

	 mockMvc.perform(post("/api/auth/validate").header("Authorization", "Bearer " + "satyam")
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
}

}
