package com.example.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.photoapp.api.users.data.AlbumsServiceClient;
import com.example.photoapp.api.users.data.UserEntity;
import com.example.photoapp.api.users.data.UserRepository;
import com.example.photoapp.api.users.shared.UserDTO;
import com.example.photoapp.api.users.ui.model.AlbumResponseModel;

import feign.FeignException;

@Service
public class UerServiceImpl implements UserService {

	private UserRepository userRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	//private RestTemplate restTemplate;
	
	private AlbumsServiceClient albumsServiceClient;
	
	private Environment env;
	
	Logger logger = LoggerFactory.getLogger(UerServiceImpl.class);

	@Autowired
	public UerServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AlbumsServiceClient albumsServiceClient, Environment env) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.albumsServiceClient = albumsServiceClient;
		this.env = env;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		userDTO.setUserId(UUID.randomUUID().toString());
		userDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
		userRepository.save(userEntity);
		return userDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return new User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			throw new UsernameNotFoundException(email);
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDetails = modelMapper.map(user, UserDTO.class);
		return userDetails;
	}

	@Override
	public UserDTO getUserDetailsByUserId(String userId) {
		UserEntity user = userRepository.findByUserId(userId);
		if (user == null)
			throw new UsernameNotFoundException(userId);
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDetails = modelMapper.map(user, UserDTO.class);
//		String albumsUrl = String.format(env.getProperty("albums.url"), userId);
//		List<AlbumResponseModel> albumList = restTemplate
//				.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//				}).getBody();
		logger.info("Before - calling albums miroservice");
		List<AlbumResponseModel> albumList = null;
		try {
			albumList = albumsServiceClient.getAlbums(userId);
		} catch (FeignException e) {
			logger.error(e.getLocalizedMessage());
		}
		logger.info("After - calling albums miroservice");
		userDetails.setAlbums(albumList);
		return userDetails;
	}
}
