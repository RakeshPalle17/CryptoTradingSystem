package com.trade.repository;

import com.trade.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOTPRepository extends JpaRepository<TwoFactorOTP, String> {

    TwoFactorOTP findByuserId(Long userId);
}
