package com.codewithfibbee.user_service_with_spring_security.service;

import com.codewithfibbee.user_service_with_spring_security.entity.PasswordToken;
import com.codewithfibbee.user_service_with_spring_security.entity.User;
import com.codewithfibbee.user_service_with_spring_security.entity.VerificationToken;
import com.codewithfibbee.user_service_with_spring_security.model.PasswordResetModel;
import com.codewithfibbee.user_service_with_spring_security.model.UserModel;
import com.codewithfibbee.user_service_with_spring_security.repository.PasswordTokenRepository;
import com.codewithfibbee.user_service_with_spring_security.repository.UserRepository;
import com.codewithfibbee.user_service_with_spring_security.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepository verificationTokenRepository;
    private PasswordTokenRepository passwordTokenRepository;


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

    @Override
    public User findUserByEmail(String email) {
         return this.userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetToken(User user, String token) {
        PasswordToken passwordToken = new PasswordToken(user, token);
        this.passwordTokenRepository.save(passwordToken);
    }

    @Override
    public String validatePasswordResetToken(String token, PasswordResetModel passwordResetModel) {
        PasswordToken passwordToken = this.passwordTokenRepository.findByToken(token);
        if(passwordToken == null) return "invalid token";
        Calendar calendar = Calendar.getInstance();
        if(passwordToken.getExpirationDate().getTime() - calendar.getTime().getTime() <= 0 ){
            this.passwordTokenRepository.delete(passwordToken);
            return "expired token";
        }
        User user = passwordToken.getUser();
        if(!Objects.equals(user.getPassword(), passwordEncoder.encode(passwordResetModel.oldPassword()))) return "Wrong old Password";
        user.setPassword(passwordEncoder.encode(passwordResetModel.newPassword()));
        this.userRepository.save(user);
        return "valid";
    }
}
