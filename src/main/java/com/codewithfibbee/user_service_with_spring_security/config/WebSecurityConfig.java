package com.codewithfibbee.user_service_with_spring_security.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }
}
