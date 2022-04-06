package com.codewithfibbee.user_service_with_spring_security.repository;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}