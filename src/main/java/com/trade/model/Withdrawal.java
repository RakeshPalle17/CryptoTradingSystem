package com.trade.model;

import java.time.LocalDateTime;

import com.trade.domain.WithDrawalStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WithDrawalStatus status;
    private Long amount;

    @ManyToOne
    private User user;

    private LocalDateTime date = LocalDateTime.now();

}
