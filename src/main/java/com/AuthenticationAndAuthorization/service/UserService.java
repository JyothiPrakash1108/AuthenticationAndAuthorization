package com.AuthenticationAndAuthorization.service;

import java.util.Date;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.AuthenticationAndAuthorization.entiy.VerificationToken;
import com.AuthenticationAndAuthorization.repo.VerificationTokenRepo;
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
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
    @Autowired
    private VerificationTokenRepo verificationTokenRepo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	public User registerUser(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(encoder.encode(userDTO.getPassword()));
		user.setCreatedAt(new Date());
		user.setEnabled(true);
		user.setRole("ADMIN");
    	return userRepo.save(user);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found with username : "+username);
		}
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole())
				.disabled(false)
				.build();
	}

    public void saveVerificationToken(User user, String verificationToken) {
        VerificationToken verificationToken1 = new VerificationToken();
        verificationToken1.setToken(verificationToken);
        verificationToken1.setUser(user);
        verificationToken1.setExpiry(new Date(System.currentTimeMillis()+ 1*60*1000));
        verificationTokenRepo.save(verificationToken1);
    }

    public String verifyRegistrationToken(String token) {
        VerificationToken registeredToken = verificationTokenRepo.findByToken(token);
        if (registeredToken == null){
            return "invalid! please try again";
        }
        if (registeredToken.getExpiry().before(new Date())){
            return "token has expired . please register again ";
        }
        User user = registeredToken.getUser();
        user.setEnabled(true);
        userRepo.save(user);
        verificationTokenRepo.delete(registeredToken);
        return "user registered sucessfully";
    }
}
