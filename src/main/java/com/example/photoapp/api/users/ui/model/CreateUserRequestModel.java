package com.example.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequestModel {

	@NotNull(message = "firstName cannot be null")
	@Size(min = 2, message = "firstName should be more than 2 characters")
	private String firstName;

	@NotNull(message = "lastName cannot be null")
	@Size(min = 2, message = "lastName should be more than 2 characters")
	private String lastName;

	@NotNull(message = "password cannot be null")
	@Size(min = 8, max = 16, message = "8 to 16 charaters")
	private String password;

	@NotNull(message = "email cannot be null")
	@Email
	private String email;
}
