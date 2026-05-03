package com.matheustorres.eadhub.payment.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheustorres.eadhub.payment.domain.models.Payment;
import com.matheustorres.eadhub.payment.domain.models.User;

public interface PaymentRepository extends JpaRepository<Payment, UUID>, JpaSpecificationExecutor<Payment> {
    Optional<Payment> findTopByUserOrderByPaymentRequestDateDesc(User user);

    @Query(value = "SELECT * FROM tb_payments WHERE user_user_id = :userId and payment_id = :paymentId", nativeQuery = true)
    Optional<Payment> findPaymentByUser(@Param("userId") UUID userId, @Param("paymentId") UUID paymentId);
}
