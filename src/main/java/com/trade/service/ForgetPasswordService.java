package com.trade.service;

import com.trade.domain.VerificationType;
import com.trade.model.ForgetPasswordToken;
import com.trade.model.User;

public interface ForgetPasswordService {
    ForgetPasswordToken createToken(User user,
                                    String id, String otp,
                                    VerificationType verificationType,
                                    String sentTo);

    ForgetPasswordToken findById(String id);
    ForgetPasswordToken findByUser(Long userId);

    void deleteToken(ForgetPasswordToken token);

}
