package com.pod4authenticationservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * This class is used to save data in database
 *
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@RequiredArgsConstructor
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "userName") })
public class UserCredential {
	/**
	 * userId automatically generated.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	/**
	 * userName is required
	 */
	@NonNull
	private String userName;
	/**
	 * password is required
	 */
	@NonNull
	private String password;
	/**
	 * roles is required
	 */
	@NonNull
	private String roles;

}
