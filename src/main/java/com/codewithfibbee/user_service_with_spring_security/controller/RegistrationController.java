package com.codewithfibbee.user_service_with_spring_security.controller;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import com.codewithfibbee.user_service_with_spring_security.entity.VerificationToken;
import com.codewithfibbee.user_service_with_spring_security.event.SuccessfulRegistrationEvent;
import com.codewithfibbee.user_service_with_spring_security.model.PasswordResetModel;
import com.codewithfibbee.user_service_with_spring_security.model.UserModel;
import com.codewithfibbee.user_service_with_spring_security.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@RestController
@AllArgsConstructor
@Slf4j
public class RegistrationController {
    private UserService userService;
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, HttpServletRequest request){
        User user = this.userService.register(userModel);
        publisher.publishEvent(new SuccessfulRegistrationEvent(user, this.generateApplicationUrl(request)));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam(name = "token") String token){
        String result = this.userService.validateRegistrationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User Verified Successfully";
        } else return "Unable to Verify User : token is " + result;
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = this.userService.generateNewVerificationToken(oldToken);
        return this.resendVerificationTokenEmail(verificationToken.getUser(), generateApplicationUrl(request), verificationToken);
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordResetModel passwordResetModel, HttpServletRequest request){
        User user = this.userService.findUserByEmail(passwordResetModel.email());
        if(user!=null){
            String token = UUID.randomUUID().toString();
            this.userService.createPasswordResetToken(user, token);
            return this.sendPasswordResetTokenEmail(user, token, generateApplicationUrl(request));
        }
        return "User not found";
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordResetModel passwordResetModel){
        String result = this.userService.validatePasswordResetToken(token, passwordResetModel);
        if(result.equalsIgnoreCase("valid")){
            return "Changed Password Successfully";
        } else return "Unable to Change User Password : " + result;
    }

    private String sendPasswordResetTokenEmail(User user, String token, String applicationUrl) {
        String url = applicationUrl + "/savePassword?token=" + token;
        log.info("Click the link to verify your account : {}", url);
        return url;
    }

    private String resendVerificationTokenEmail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        log.info("Click the link to verify your account : {}", url);
        return "new token sent";
    }

    private String generateApplicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
