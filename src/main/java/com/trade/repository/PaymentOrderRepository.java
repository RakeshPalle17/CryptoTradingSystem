package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    

}
