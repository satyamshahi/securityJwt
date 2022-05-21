/**
 * 
 */
package com.pod4authenticationservice.security.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 *This is JwtSignature Test class
 *
 */
class JwtSignatureTest {

	/**
	 * Test method for
	 * {@link com.pod4authenticationservice.security
	 * .service.JwtSignature#getJwtSecret()}.
	 */
	@Test
	public void testGetJwtSecret() {
		final JwtSignature jwtSignature = new JwtSignature();
		 jwtSignature.setJwtSecret("secret");
		assertEquals("secret", jwtSignature.getJwtSecret());
	}

	/**
	 * Test method for
	 * {@link com.pod4authenticationservice.security.
	 * service.JwtSignature#getJwtExpirationMs()}.
	 */
	@Test
	public void testGetJwtExpirationMs() {
		final JwtSignature jwtSignature = new JwtSignature();
		jwtSignature.setJwtExpirationMs(500);
		assertEquals(500, jwtSignature.getJwtExpirationMs());
	}

}
