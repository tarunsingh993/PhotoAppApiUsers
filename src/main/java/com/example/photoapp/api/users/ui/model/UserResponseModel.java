package com.example.photoapp.api.users.ui.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel {
	private String firstName;
	private String lastName;
	private String email;
	private String userId;
	private List<AlbumResponseModel> albums;
}
