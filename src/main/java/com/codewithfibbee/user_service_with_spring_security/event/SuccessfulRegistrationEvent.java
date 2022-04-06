package com.codewithfibbee.user_service_with_spring_security.event;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import org.springframework.context.ApplicationEvent;

public class SuccessfulRegistrationEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public SuccessfulRegistrationEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
