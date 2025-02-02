package com.trade.service;

import com.trade.model.TwoFactorOTP;
import com.trade.model.User;
import com.trade.repository.TwoFactorOTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOTPServiceImpl implements TwoFactorOTPService{

    @Autowired
    private TwoFactorOTPRepository twoFactorOTPRepository;

    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();
        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setUser(user);
        twoFactorOTP.setId(id);
        return twoFactorOTPRepository.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> otp = twoFactorOTPRepository.findById(id);
        return otp.orElse(null);
    }

    @Override
    public TwoFactorOTP findByUserId(Long userId) {
        return twoFactorOTPRepository.findByuserId(userId);
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
        twoFactorOTPRepository.delete(twoFactorOTP);

    }
}
