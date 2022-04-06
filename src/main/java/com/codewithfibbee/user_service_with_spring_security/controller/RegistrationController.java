package com.codewithfibbee.user_service_with_spring_security.controller;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import com.codewithfibbee.user_service_with_spring_security.model.UserModel;
import com.codewithfibbee.user_service_with_spring_security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationController {
    private UserService userService;
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel){
        User user = this.userService.register(userModel);
        publisher.publishEvent();
        return "Success";
    }
}
