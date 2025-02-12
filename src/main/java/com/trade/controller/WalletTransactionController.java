package com.trade.controller;

import com.trade.domain.WalletTransactionType;
import com.trade.model.Wallet;
import com.trade.model.WalletTransaction;
import com.trade.service.WalletTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet-transactions")
public class WalletTransactionController {

    @Autowired
    private WalletTransactionService walletTransactionService;

    @PostMapping
    public ResponseEntity<WalletTransaction> createWalletTransaction(
            @RequestParam Long walletId,
            @RequestParam WalletTransactionType type,
            @RequestParam String transferId,
            @RequestParam String description) {

        Wallet wallet = walletTransactionService.getWalletById(walletId);
        WalletTransaction walletTransaction = walletTransactionService.createWalletTransaction(
                wallet, type, transferId, description, walletId);

        return ResponseEntity.ok(walletTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletTransactionById(@PathVariable Long id) {
        Wallet wallet = walletTransactionService.getWalletById(id);
        return ResponseEntity.ok(wallet);
    }
}