package com.trade.service;

import java.util.List;

import com.trade.domain.WalletTransactionType;
import com.trade.model.Wallet;
import com.trade.model.WalletTransaction;

public interface TransactionService {

    WalletTransaction createWalletTransaction(Wallet userWallet, WalletTransactionType type, Long transferId,
            String description, Long walletId);
            
    List<WalletTransaction> getTransactionsByWallet(Wallet wallet);

}
