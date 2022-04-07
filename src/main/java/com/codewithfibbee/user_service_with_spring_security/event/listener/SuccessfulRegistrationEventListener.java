package com.codewithfibbee.user_service_with_spring_security.event.listener;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import com.codewithfibbee.user_service_with_spring_security.event.SuccessfulRegistrationEvent;
import com.codewithfibbee.user_service_with_spring_security.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SuccessfulRegistrationEventListener implements ApplicationListener<SuccessfulRegistrationEvent> {

    private UserService userService;
    @Override
    public void onApplicationEvent(SuccessfulRegistrationEvent event) {
        // create the verification token for the user
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        this.userService.saveUserVerificationToken(user, token);

        // send mail to the user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        //TODO send verification email method goes here
        log.info("Click the link to verify your account : {}", url);
    }
}
