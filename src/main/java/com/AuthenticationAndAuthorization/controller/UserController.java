package com.AuthenticationAndAuthorization.controller;

import com.AuthenticationAndAuthorization.dto.UserDTO;
import com.AuthenticationAndAuthorization.entiy.User;
import com.AuthenticationAndAuthorization.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@PostMapping("/register")
	public User registerUser(@RequestBody UserDTO userDTO) {
		return userService.registerUser(userDTO);
	}
}
