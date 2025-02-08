package com.trade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trade.domain.PaymentMethod;
import com.trade.model.PaymentOrder;
import com.trade.model.User;
import com.trade.repository.PaymentOrderRepository;
import com.trade.response.PaymentResponse;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Value("${razorpay.api.key}")
    private String razorpayKey;

    @Value("${razorpay.api.secret}")
    private String razorpaySecret;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;


    @Override
    public PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);

        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaymentOrderById'");
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'proceedPaymentOrder'");
    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRazorpayPaymentLink'");
    }

    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createStripePaymentLink'");
    }

}
