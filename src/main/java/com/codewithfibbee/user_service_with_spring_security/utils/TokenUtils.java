package com.codewithfibbee.user_service_with_spring_security.utils;

import java.util.Calendar;
import java.util.Date;

public class TokenUtils {
    // expiration time = 10minutes
    private static final int EXPIRATION_TIME = 10;

    public static Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
