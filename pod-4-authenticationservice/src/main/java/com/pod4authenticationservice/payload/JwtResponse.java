package com.pod4authenticationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


/**
 * This class is used to store jwtResponse
 *
 */
@Getter
@AllArgsConstructor
public class JwtResponse {
	/**
	 * 
	 */
	private long userId;
	/**
	 * 
	 */
	private String token;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private List<String> roles;
}
