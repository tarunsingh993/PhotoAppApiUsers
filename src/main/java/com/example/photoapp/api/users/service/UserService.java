package com.example.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.photoapp.api.users.shared.UserDTO;

public interface UserService extends UserDetailsService {
	UserDTO createUser(UserDTO userDTO);
	UserDTO getUserDetailsByEmail(String email);
	UserDTO getUserDetailsByUserId(String userId);
}
