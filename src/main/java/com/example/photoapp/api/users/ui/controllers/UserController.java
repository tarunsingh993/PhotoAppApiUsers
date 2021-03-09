package com.example.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.photoapp.api.users.service.UserService;
import com.example.photoapp.api.users.shared.UserDTO;
import com.example.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.example.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.example.photoapp.api.users.ui.model.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Environment env;

	@Autowired
	private UserService userService;

	@GetMapping("/status/check")
	public String status() {
		return "Working on: " + env.getProperty("local.server.port") + ", with token: "
				+ env.getProperty("token.secret") + ", with common: " + env.getProperty("test.common");
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CreateUserResponseModel> createUser(
			@Valid @RequestBody CreateUserRequestModel userRequestModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelMapper.map(userRequestModel, UserDTO.class);
		userDTO = userService.createUser(userDTO);
		CreateUserResponseModel createUserResponseModel = modelMapper.map(userDTO, CreateUserResponseModel.class);
		return new ResponseEntity<CreateUserResponseModel>(createUserResponseModel, HttpStatus.CREATED);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserResponseModel> getUser(@PathVariable("id") String userId) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = userService.getUserDetailsByUserId(userId);
		UserResponseModel userResponseModel = modelMapper.map(userDTO, UserResponseModel.class);
		return new ResponseEntity<UserResponseModel>(userResponseModel, HttpStatus.OK);
	}
}
