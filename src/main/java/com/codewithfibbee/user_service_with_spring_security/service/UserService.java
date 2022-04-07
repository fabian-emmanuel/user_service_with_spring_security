package com.codewithfibbee.user_service_with_spring_security.service;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import com.codewithfibbee.user_service_with_spring_security.entity.VerificationToken;
import com.codewithfibbee.user_service_with_spring_security.model.UserModel;

public interface UserService {
    User register(UserModel userModel);

    void saveUserVerificationToken(User user, String token);

    String validateRegistrationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
