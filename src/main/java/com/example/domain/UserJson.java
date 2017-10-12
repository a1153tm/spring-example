
package com.example.domain;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class UserJson {

	@Unique
	private String firstName;

	@NotBlank
	private String lastName;

	private Role role;

	private EmailAddress emailAddress;

}