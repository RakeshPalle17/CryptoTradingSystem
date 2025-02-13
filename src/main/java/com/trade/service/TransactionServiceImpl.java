package com.trade.service;

import com.trade.domain.WalletTransactionType;
import com.trade.model.Wallet;
import com.trade.model.WalletTransaction;
import com.trade.repository.TransactionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository walletTransactionRepository;

    @Override
    public WalletTransaction createWalletTransaction(Wallet userWallet, WalletTransactionType type, Long transferId,
            String purpose, Long walletId) {
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setWallet(userWallet);
        walletTransaction.setType(type);
        walletTransaction.setTransferId(transferId);
        walletTransaction.setPurpose(purpose);
        walletTransaction.setId(walletId);

        return walletTransactionRepository.save(walletTransaction);
    }


    @Override
    public List<WalletTransaction> getTransactionsByWallet(Wallet wallet) {
        return walletTransactionRepository.findByWallet(wallet);
    }


}
