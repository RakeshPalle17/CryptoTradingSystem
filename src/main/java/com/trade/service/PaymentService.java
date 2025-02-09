package com.trade.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.trade.domain.PaymentMethod;
import com.trade.model.PaymentOrder;
import com.trade.model.User;
import com.trade.response.PaymentResponse;

public interface PaymentService {

    PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id);
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLink(User user, Long amount);
    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;



}
