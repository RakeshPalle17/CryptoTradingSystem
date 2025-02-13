package com.trade.service;

import com.trade.domain.WalletTransactionType;
import com.trade.model.Wallet;
import com.trade.model.WalletTransaction;
import com.trade.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionService;

    @Override
    public WalletTransaction createWalletTransaction(Wallet userWallet, WalletTransactionType type, Long transferId,
            String purpose, Long amount) {
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setWallet(userWallet);
        walletTransaction.setType(type);
        walletTransaction.setTransferId(transferId);
        walletTransaction.setPurpose(purpose);
        walletTransaction.setAmount(amount);
        walletTransaction.setDate(LocalDateTime.now());

        return transactionService.save(walletTransaction);
    }


    @Override
    public List<WalletTransaction> getTransactionsByWallet(Wallet wallet) {
        return transactionService.findByWallet(wallet);
    }


}
