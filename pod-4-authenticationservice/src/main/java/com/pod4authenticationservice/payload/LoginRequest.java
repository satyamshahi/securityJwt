package com.pod4authenticationservice.payload;



import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;


/**
 * This class is used to store the LogineRequest
 *
 */
@Getter
@Setter
public class LoginRequest {
	/**
	 * 
	 */
	@Pattern(regexp="^[a-zA-Z0-9].*")
    private String userName;
	/**
	 * 
	 */
	@Pattern(regexp="^[a-zA-Z0-9].*")
    private String password;

	
}
