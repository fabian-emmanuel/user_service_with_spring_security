package com.codewithfibbee.user_service_with_spring_security.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static com.codewithfibbee.user_service_with_spring_security.utils.TokenUtils.calculateExpirationDate;

@Entity
@Data
@NoArgsConstructor
public class PasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_TOKEN"))
    private User user;

    private String token;
    private Date expirationDate;

    public PasswordToken(User user, String token) {
        super();
        this.user = user;
        this.token = token;
        this.expirationDate = calculateExpirationDate();
    }

    public PasswordToken(String token) {
        super();
        this.token = token;
        this.expirationDate = calculateExpirationDate();
    }
}
