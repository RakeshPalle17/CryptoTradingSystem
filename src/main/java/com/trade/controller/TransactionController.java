package com.trade.controller;

import com.trade.model.User;
import com.trade.model.Wallet;
import com.trade.model.WalletTransaction;
import com.trade.service.UserService;
import com.trade.service.WalletService;
import com.trade.service.TransactionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getUserWallet(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        List<WalletTransaction> transactionList = transactionService.getTransactionsByWallet(wallet);

        return new ResponseEntity<>(transactionList, HttpStatus.ACCEPTED);

    }
}