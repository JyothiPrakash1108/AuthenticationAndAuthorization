package com.AuthenticationAndAuthorization.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.AuthenticationAndAuthorization.dto.UserDTO;
import com.AuthenticationAndAuthorization.entiy.User;
import com.AuthenticationAndAuthorization.repo.UserRepo;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	public User registerUser(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(encoder.encode(userDTO.getPassword()));
		user.setCreatedAt(new Date());
		user.setEnabled(false);
		user.setRole("ADMIN");
		return userRepo.save(user);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			throw new UsernameNotFoundException("user not found with username : "+username);
		}
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole())
				.disabled(false)
				.build();
	}

}
