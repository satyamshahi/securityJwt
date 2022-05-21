package com.pod4authenticationservice.security.service;

import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pod4authenticationservice.entity.UserCredential;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Satyavrat
 *
 */
@Getter
public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private long userId;
	/**
	 * 
	 */
	private String userName;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private List<GrantedAuthority> authorities;

	/**
	 * @param userCredential This is used to Store user credential 
	 */
	public UserDetailsImpl(final UserCredential userCredential) {
		this.userId = userCredential.getUserId();
		this.userName = userCredential.getUserName();
		this.password = userCredential.getPassword();
		this.authorities = Arrays.stream(userCredential.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	/**
	 *This @Override method is used for getting Authorities.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 *
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 *
	 */
	@Override
	public String getUsername() {
		return userName;
	}

	/**
	 *
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 *
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 *
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 *
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}
