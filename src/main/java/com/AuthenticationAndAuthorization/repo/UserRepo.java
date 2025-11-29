package com.AuthenticationAndAuthorization.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AuthenticationAndAuthorization.entiy.User;
@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
