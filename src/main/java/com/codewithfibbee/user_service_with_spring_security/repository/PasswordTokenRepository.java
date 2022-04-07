package com.codewithfibbee.user_service_with_spring_security.repository;

import com.codewithfibbee.user_service_with_spring_security.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

    @Query("select p from PasswordToken p where p.token = ?1")
    PasswordToken findByToken(String token);
}