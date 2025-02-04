package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
