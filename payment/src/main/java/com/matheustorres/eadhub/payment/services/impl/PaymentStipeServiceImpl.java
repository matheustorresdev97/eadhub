package com.matheustorres.eadhub.payment.services.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.payment.domain.enums.PaymentControl;
import com.matheustorres.eadhub.payment.domain.models.CreditCard;
import com.matheustorres.eadhub.payment.domain.models.Payment;
import com.matheustorres.eadhub.payment.services.PaymentStipeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PaymentStipeServiceImpl implements PaymentStipeService {

    @Override
    public Payment processStripePayment(Payment payment, CreditCard creditCard) {
        log.info("Processing Stripe payment for paymentId: {}", payment.getPaymentId());
        
        // Mocking Stripe processing
        if (creditCard.getCvvCode().equals("000")) {
             return Payment.builder()
                    .paymentId(payment.getPaymentId())
                    .paymentControl(PaymentControl.REFUSED)
                    .paymentRequestDate(payment.getPaymentRequestDate())
                    .paymentCompletionDate(LocalDateTime.now())
                    .paymentExpirationDate(payment.getPaymentExpirationDate())
                    .lastDigitsCreditCard(payment.getLastDigitsCreditCard())
                    .valuePaid(payment.getValuePaid())
                    .paymentMessage("Stripe error: Invalid CVV")
                    .recurrence(payment.isRecurrence())
                    .user(payment.getUser())
                    .build();
        }

        return Payment.builder()
                .paymentId(payment.getPaymentId())
                .paymentControl(PaymentControl.EFFECTED)
                .paymentRequestDate(payment.getPaymentRequestDate())
                .paymentCompletionDate(LocalDateTime.now())
                .paymentExpirationDate(payment.getPaymentExpirationDate())
                .lastDigitsCreditCard(payment.getLastDigitsCreditCard())
                .valuePaid(payment.getValuePaid())
                .paymentMessage("Stripe success")
                .recurrence(payment.isRecurrence())
                .user(payment.getUser())
                .build();
    }
}
