package com.trade.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.trade.model.PaymentDetails;
import com.trade.model.User;
import com.trade.repository.PaymentDetailRepository;

public class PaymentDetailsServiceImpl implements PaymentDetailsService {
    @Autowired
    private PaymentDetailRepository paymentDetailsRepository;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String bankName, String accountHolderName,
            String ifscCode, User user) {

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setBankName(bankName);
        paymentDetails.setIfsc(ifscCode);
        paymentDetails.setUser(user);

        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepository.findByUserId(user.getId());
    }

}
