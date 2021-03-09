package com.example.photoapp.api.users.shared;

import java.io.Serializable;
import java.util.List;

import com.example.photoapp.api.users.ui.model.AlbumResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6084467491654736116L;

	private String firstName;

	private String lastName;

	private String password;

	private String email;

	private String userId;

	private String encryptedPassword;

	private List<AlbumResponseModel> albums;

}
