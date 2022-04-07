package com.codewithfibbee.user_service_with_spring_security.repository;

import com.codewithfibbee.user_service_with_spring_security.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Query("select v from VerificationToken v where v.token = ?1")
    VerificationToken findByToken(String token);
}