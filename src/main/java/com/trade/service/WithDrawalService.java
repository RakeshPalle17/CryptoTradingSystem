package com.trade.service;

import java.util.List;

import com.trade.model.User;
import com.trade.model.Withdrawal;

public interface WithDrawalService {
    Withdrawal requestWithDrawal(Long amount, User user);

    Withdrawal proceedWithDrawal(Long withdrawalId, boolean accept);

    List<Withdrawal> getUsersWithDrawalHistory(User user);

    List<Withdrawal> getAllWithDrawalRequest();
}
