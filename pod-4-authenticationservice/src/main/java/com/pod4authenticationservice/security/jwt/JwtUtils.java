package com.pod4authenticationservice.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pod4authenticationservice.security.service.JwtSignature;

import java.util.Date;

/**
 * This is JwtUtils class
 *
 */
@Component
@Slf4j
public class JwtUtils {

	/**
	 * 
	 */
	@Autowired
	private transient JwtSignature jwtSignature;

	/**
	 * @param userName 
	 * This method generated the jwt token using userName,
	 *  secret Key and jwt Expiration time.
	 */
	public String generateJwtToken(final String userName) {
		log.info("generateJwtToken method started...");

		final int jwtExpiration = jwtSignature.getJwtExpirationMs();

		final String secret = jwtSignature.getJwtSecret();

		return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	/**
	 * @param token This is Jwt token.
	 * This method is used to get userName from Jwt token.
	 */
	public String getUserNameFromJwtToken(final String token) {
		log.info("getUserNameFromJwtToken is started...");
		final String jwtSecret = jwtSignature.getJwtSecret();
		log.debug("Extracting userName from Jwt token");
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * @param authToken This is Jwt token.
	 * This method is used validation of user Jwt token.
	 */
	public ResponseEntity<String> validateJwtToken(final String authToken) {
		log.info("validateJwtToken method is started...");
		try {
			final String jwtSecret = jwtSignature.getJwtSecret();
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			
			
			return new ResponseEntity<>("Validated Successfully....",HttpStatus.OK);

		} catch (Exception e) {
			log.error("JWT Token is Not Valid: {}", e.getMessage());
			
			
			return new ResponseEntity<>("JWT Token is Not Valid ",HttpStatus.UNAUTHORIZED);
		}
		
	}
}
