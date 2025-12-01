package com.AuthenticationAndAuthorization.repo;

import com.AuthenticationAndAuthorization.entiy.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
