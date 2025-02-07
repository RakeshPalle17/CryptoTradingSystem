package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.model.PaymentDetails;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetails, Long> {
    PaymentDetails findByUserId(Long userId);

}
