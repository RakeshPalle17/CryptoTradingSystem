package com.trade.service;

import com.trade.domain.WalletTransactionType;
import com.trade.model.Wallet;
import com.trade.model.WalletTransaction;

public interface WalletTransactionService {

    WalletTransaction createWalletTransaction(Wallet userWallet, WalletTransactionType type, String transferId,
            String description, Long walletId);

    Wallet getWalletById(Long id);

}
