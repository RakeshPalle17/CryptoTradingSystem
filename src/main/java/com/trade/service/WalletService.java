package com.trade.service;

import com.trade.model.Order;
import com.trade.model.User;
import com.trade.model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet, Long amount);

    Wallet findWalletById(Long id);

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount);

    Wallet payOrderPayment(Order order, User user);


}
