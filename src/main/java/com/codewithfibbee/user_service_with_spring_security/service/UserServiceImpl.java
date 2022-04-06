package com.codewithfibbee.user_service_with_spring_security.service;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import com.codewithfibbee.user_service_with_spring_security.model.UserModel;
import com.codewithfibbee.user_service_with_spring_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public User register(UserModel userModel) {
        User user = User.builder()
                .email(userModel.email())
                .firstName(userModel.firstName())
                .lastName(userModel.lastName())
                .password(passwordEncoder.encode(userModel.password()))
                .role("ROLE")
                .build();
        return this.userRepository.save(user);
    }
}
