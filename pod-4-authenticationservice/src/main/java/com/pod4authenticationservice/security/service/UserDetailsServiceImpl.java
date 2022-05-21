package com.pod4authenticationservice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pod4authenticationservice.entity.UserCredential;
import com.pod4authenticationservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * This class is used for load User Credential
 *
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * 
	 */
	private transient final UserRepository userRepository;

	/**
	 * @param userRepository
	 */
	@Autowired
	public UserDetailsServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 *This method is used to load userCredentials from h2 database
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
		log.info("loadUserByUsername method is started...");
		final Optional<UserCredential> userCredentials = userRepository.findByUserName(userName);

		return userCredentials.map(UserDetailsImpl::new).get();
	}
}
