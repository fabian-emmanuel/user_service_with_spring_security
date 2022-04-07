package com.codewithfibbee.user_service_with_spring_security.model;

import lombok.Builder;

@Builder
public record PasswordResetModel(String email, String oldPassword, String newPassword) {
}
