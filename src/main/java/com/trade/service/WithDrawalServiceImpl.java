package com.trade.service;

import java.lang.StackWalker.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.domain.WithDrawalStatus;
import com.trade.model.User;
import com.trade.model.Withdrawal;
import com.trade.repository.WithDrawalRepository;

import lombok.With;

@Service
public class WithDrawalServiceImpl implements WithDrawalService {

    @Autowired
    private WithDrawalRepository withDrawalRepository;

    @Override
    public Withdrawal requestWithDrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithDrawalStatus.PENDING);

        return withDrawalRepository.save(withdrawal);

    }

    @Override
    public Withdrawal proceedWithDrawal(Long withdrawalId, boolean accept) {
        Optional<Withdrawal> withdrawal = withDrawalRepository.findById(withdrawalId);
        if (withdrawal.isEmpty()) {
            throw new RuntimeException("Withdrawal not found");
        }
        Withdrawal withdrawal1 = withdrawal.get();
        withdrawal1.setDate(LocalDateTime.now());

        if (accept) {
            withdrawal1.setStatus(WithDrawalStatus.SUCCESS);
        } else {
            withdrawal1.setStatus(WithDrawalStatus.PENDING);
        }
        return withDrawalRepository.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithDrawalHistory(User user) {
        return withDrawalRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithDrawalRequest() {
        return withDrawalRepository.findAll();
    }

}