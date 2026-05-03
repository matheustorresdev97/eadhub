package com.matheustorres.eadhub.payment.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentEventoDTO(
        UUID paymentId,
        String paymentControl,
        LocalDateTime paymentRequestDate,
        LocalDateTime paymentCompletionDate,
        LocalDateTime paymentExpirationDate,
        String lastDigitsCreditCard,
        BigDecimal valuePaid,
        String paymentMessage,
        boolean recurrence,
        UUID userId) {

}
