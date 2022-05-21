package com.pod4authenticationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pod4authenticationservice.entity.UserCredential;
import com.pod4authenticationservice.repository.UserRepository;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

/**
 * Authentication Microservice's main spring boot application class
 *
 */
@Slf4j
@SpringBootApplication
public class Pod4AuthenticationserviceApplication implements CommandLineRunner {

	/**
	 * this object use for save data in database or get data from database etc.
	 */
	private transient final UserRepository userRepository;
	/**
	 * this object is use for encoding password.
	 */
	private transient final PasswordEncoder passwordEncoder;

	/**
	 * @param userRepository  - this object use for save data in database or get
	 *                        data from database etc.
	 * @param passwordEncoder - this object is use for encoding password
	 */
	@Autowired
	public Pod4AuthenticationserviceApplication(final UserRepository userRepository,
			final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * @param args the command line arguments
	 */
	@Generated
	public static void main(final String[] args) {

		log.info("Main application Class is started");
		SpringApplication.run(Pod4AuthenticationserviceApplication.class, args);
		log.info("Main application running");
	}

	/**
	 * This override method use hear to save the UserCredential credential in
	 * database.
	 */
	@Override
	public void run(final String... args) throws Exception {
		log.info("user credential save in database");
		userRepository.save(new UserCredential("satyam1", passwordEncoder.encode("12345"), "ROLE_USER"));
		userRepository.save(new UserCredential("satyam2", passwordEncoder.encode("12345"), "ROLE_ADMIN"));
	}
}
