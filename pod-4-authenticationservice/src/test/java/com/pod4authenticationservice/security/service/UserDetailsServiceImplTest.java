/**
 * 
 */
package com.pod4authenticationservice.security.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.pod4authenticationservice.entity.UserCredential;
import com.pod4authenticationservice.repository.UserRepository;


/**
 * UserDetailsServiceImpl Testing Class
 *
 */
class UserDetailsServiceImplTest {

	/**
	 * 
	 */
	@Mock
	public transient UserRepository userRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test method for
	 * {@link com.authenticationmicroservice.security.service
	 * .UserDetailsServiceImpl#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testLoadUserByUsernameWhenuserPresentInRepository() {

		final UserDetailsServiceImpl udsi = new UserDetailsServiceImpl(userRepository);

		final UserCredential userCredentials = new UserCredential();
		userCredentials.setUserId(1l);
		userCredentials.setPassword("12345");
		userCredentials.setRoles("USER");
		userCredentials.setUserName("user1");

		final Optional<UserCredential> userCredential = Optional.of(userCredentials);
		when(userRepository.findByUserName("user1")).thenReturn(userCredential);

		final UserDetails result = udsi.loadUserByUsername("user1");

		assertNotNull(result);
		verify(userRepository).findByUserName("user1");
	}

	/**
	 * Test method for
	 * {@link com.authenticationmicroservice.security.service
	 * .UserDetailsServiceImpl#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testLoadUserByUsernameWhenuserIsPresentInRepository() throws Exception {
		final UserDetailsServiceImpl udsi = new UserDetailsServiceImpl(userRepository);
		final UserCredential userCredentials = new UserCredential();
		userCredentials.setUserId(1l);
		userCredentials.setPassword("12345");
		userCredentials.setRoles("USER");
		userCredentials.setUserName("user");


		final Optional<UserCredential> userCredential = Optional.of(userCredentials);
		when(userRepository.findByUserName("user")).thenReturn(userCredential);
		assertNotNull(udsi.loadUserByUsername("user"));

	}

}
