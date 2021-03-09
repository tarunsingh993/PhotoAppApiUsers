package com.example.photoapp.api.users.ui.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserResponseModel {
	private String firstName;
	private String lastName;
	private String email;
	private String userId;
}
