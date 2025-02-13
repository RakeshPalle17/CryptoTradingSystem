package com.trade.repository;

import com.trade.model.Wallet;
import com.trade.model.WalletTransaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByWallet(Wallet wallet);
}