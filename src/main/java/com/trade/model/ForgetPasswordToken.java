package com.trade.model;

import com.trade.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ForgetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String otp;

    @OneToOne
    private User user;

    private String sentTo;

    private VerificationType verificationType;
}
