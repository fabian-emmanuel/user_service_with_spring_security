package com.codewithfibbee.user_service_with_spring_security.model;

import lombok.Builder;


@Builder
public record UserModel(String firstName, String lastName, String email, String password, String confirmPassword) {
}
