package com.pod4authenticationservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pod4authenticationservice.payload.JwtResponse;
import com.pod4authenticationservice.payload.LoginRequest;
import com.pod4authenticationservice.repository.UserRepository;
import com.pod4authenticationservice.security.jwt.JwtUtils;
import com.pod4authenticationservice.security.service.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * This is authentication controller class
 *
 */
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

	/**
	 * This is object of AuthenticationManager. use for creating Authentication
	 * object
	 */
	private transient final AuthenticationManager authManager;
	/**
	 * This is object of UserRepository. use for save,update,get data from dataBase
	 */
	private transient final UserRepository userRepository;
	/**
	 * This is the object of PasswordEncoder. use for encoding password.
	 */
	private transient final PasswordEncoder encoder;
	/**
	 * This is object of JwtUtils class.
	 * 
	 */
	private transient final JwtUtils jwtUtils;

	/**
	 * @param authenticationManager
	 * @param jwtUtils
	 * @param userRepository
	 * @param encoder
	 */
	public AuthController(final AuthenticationManager authManager, final JwtUtils jwtUtils,
			final UserRepository userRepository, final PasswordEncoder encoder) {
		this.authManager = authManager;
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	/**
	 * @param loginRequest This parameter is Object of LoginRequest Class and
	 *                     Contains UserName and Password. This Rest Point is used
	 *                     for UserCredential Signing and generate Jwt token.
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest,
			final BindingResult result) {

		if (result.hasErrors()) {
			log.info("Blank username and/or password!");
			return new ResponseEntity<>("Blank username and/or password!", HttpStatus.BAD_REQUEST);
		} else {
			log.info("Login Request {}", loginRequest.getUserName());

			final Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

			final SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			final String jwt = jwtUtils.generateJwtToken(userPrincipal.getUsername());

			final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			final List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			final ResponseEntity<JwtResponse> response = new ResponseEntity<>(
					new JwtResponse(userDetails.getUserId(), jwt, userDetails.getUsername(), roles), HttpStatus.OK);
			log.info("End -> ", response);
			return response;
		}

	}

	/**
	 * @param token -> This is Jwt token.
	 * @return
	 * 
	 *         This Rest Point is used for Validate the user, using Jwt token.
	 */
	@PostMapping("/validate")
	public ResponseEntity<String> validateAndReturnUser(@RequestHeader("Authorization") final String token) {
		final String jwttoken = token.substring(7);
		log.debug("in auth controller with jwt {}", jwttoken);
		return jwtUtils.validateJwtToken(jwttoken);

	}
}
