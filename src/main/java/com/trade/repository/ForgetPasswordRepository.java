package com.trade.repository;

import com.trade.model.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgetPasswordRepository extends JpaRepository<ForgetPasswordToken, String> {
    ForgetPasswordToken findByUserId(Long userId);
}
