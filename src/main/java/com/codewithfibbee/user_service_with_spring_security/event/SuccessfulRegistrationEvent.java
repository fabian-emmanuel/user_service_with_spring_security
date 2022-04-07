package com.codewithfibbee.user_service_with_spring_security.event;

import com.codewithfibbee.user_service_with_spring_security.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SuccessfulRegistrationEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public SuccessfulRegistrationEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
