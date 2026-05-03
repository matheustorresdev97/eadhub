package com.matheustorres.eadhub.payment.services;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.matheustorres.eadhub.payment.domain.models.Payment;
import com.matheustorres.eadhub.payment.domain.models.User;
import com.matheustorres.eadhub.payment.dtos.PaymentCommandDTO;
import com.matheustorres.eadhub.payment.dtos.PaymentDTO;

public interface PaymentService {
    Payment requestPayment(PaymentDTO paymentDTO, User user);

    Optional<Payment> findLastPaymentByUser(User user);

    Page<Payment> findAllByUser(Specification<Payment> spec, Pageable pageable);

    Optional<Payment> findPaymentByUser(UUID userId, UUID paymentId);

    void makePayment(PaymentCommandDTO paymentCommandDTO);
}
