package com.codewithfibbee.user_service_with_spring_security.service;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import com.codewithfibbee.user_service_with_spring_security.entity.VerificationToken;
import com.codewithfibbee.user_service_with_spring_security.model.UserModel;
import com.codewithfibbee.user_service_with_spring_security.repository.UserRepository;
import com.codewithfibbee.user_service_with_spring_security.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepository verificationTokenRepository;


    @Override
    public User register(UserModel userModel) {
        User user = User.builder()
                .email(userModel.email())
                .firstName(userModel.firstName())
                .lastName(userModel.lastName())
                .password(passwordEncoder.encode(userModel.password()))
                .role("USER")
                .build();
        return this.userRepository.save(user);
    }

    @Override
    public void saveUserVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        this.verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateRegistrationToken(String token) {
        VerificationToken verificationToken = this.verificationTokenRepository.findByToken(token);
        if(verificationToken == null) return "invalid";
        Calendar calendar = Calendar.getInstance();
        if(verificationToken.getExpirationDate().getTime() - calendar.getTime().getTime() <= 0 ){
            this.verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        User user = verificationToken.getUser();
        user.setActive(true);
        this.userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = this.verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        return this.verificationTokenRepository.save(verificationToken);
    }
}
