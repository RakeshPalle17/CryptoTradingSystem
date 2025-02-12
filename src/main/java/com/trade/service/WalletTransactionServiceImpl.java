package com.trade.service;

import com.trade.domain.WalletTransactionType;
import com.trade.model.Wallet;
import com.trade.model.WalletTransaction;
import com.trade.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public WalletTransaction createWalletTransaction(Wallet userWallet, WalletTransactionType type, String transferId,
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
    public Wallet getWalletById(Long id) {
        WalletTransaction walletTransaction = walletTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return walletTransaction.getWallet();
    }
}
