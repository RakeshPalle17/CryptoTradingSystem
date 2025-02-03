package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);
}
