package com.example.photoapp.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import feign.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class PhotoAppApiUsersApplication {
	
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Logger.Level feignloggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	@Profile("production")
	public String createProductionBean() {
		System.out.println("Production--------- " + env.getProperty("myapp.environment"));
		return "Production";
	}

	@Bean
	//@Profile("default")
	public String createDefaultBean() {
		System.out.println("Default--------- " + env.getProperty("myapp.environment"));
		return "Default";
	}

	@Bean
	@Profile("!production")
	public String createNotProductionBean() {
		System.out.println("Not Production--------- " + env.getProperty("myapp.environment"));
		return "Not Production";
	}
}
