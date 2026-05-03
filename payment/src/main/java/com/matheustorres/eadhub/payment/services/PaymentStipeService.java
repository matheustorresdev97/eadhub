package com.matheustorres.eadhub.payment.services;

import com.matheustorres.eadhub.payment.domain.models.CreditCard;
import com.matheustorres.eadhub.payment.domain.models.Payment;

public interface PaymentStipeService {
    Payment processStripePayment(Payment payment, CreditCard creditCard);
}
