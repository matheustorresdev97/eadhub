package com.matheustorres.eadhub.payment.dtos;

import java.util.UUID;

public record PaymentCommandDTO(
        UUID userId,
        UUID paymentId,
        UUID cardId) {

}
