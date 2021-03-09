package com.example.photoapp.api.users.ui.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestModel {

	private String email;

	private String password;
}
