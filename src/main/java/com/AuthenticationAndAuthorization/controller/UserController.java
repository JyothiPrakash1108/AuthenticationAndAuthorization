package com.AuthenticationAndAuthorization.controller;

import com.AuthenticationAndAuthorization.dto.UserDTO;
import com.AuthenticationAndAuthorization.entiy.User;
import com.AuthenticationAndAuthorization.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
	@PostMapping("/register")
	public User registerUser(@RequestBody UserDTO userDTO) {
        User user = userService.registerUser(userDTO);
        String verificationToken = UUID.randomUUID().toString();
        String verificationURL = "http://localhost:8080/register/verifyRegistrationToken?token="+verificationToken;
		System.out.println("Verify the user by clicking the following link : "+verificationURL);
        userService.saveVerificationToken(user,verificationToken);
        return user;
	}
    @PostMapping("/register/verifyRegistrationToken")
    public String verifyRegistration(@RequestParam String token){
        return userService.verifyRegistrationToken(token);
    }
    @GetMapping("/hey")
    public String greet(){
        return "hey";
    }

    @PostMapping("/signin")
    public String login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        if(auth.isAuthenticated()){
            return userService.generateToken(username);
        }
        return "signin failed";
    }
}
