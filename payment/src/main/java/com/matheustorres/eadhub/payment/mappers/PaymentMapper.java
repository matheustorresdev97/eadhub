package com.matheustorres.eadhub.payment.mappers;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.payment.domain.enums.PaymentControl;
import com.matheustorres.eadhub.payment.domain.models.CreditCard;
import com.matheustorres.eadhub.payment.domain.models.Payment;
import com.matheustorres.eadhub.payment.domain.models.User;
import com.matheustorres.eadhub.payment.dtos.PaymentDTO;
import com.matheustorres.eadhub.payment.dtos.PaymentEventoDTO;

@Component
public class PaymentMapper {

    public Payment toPaymentEntity(PaymentDTO paymentDTO, User user) {
        return Payment.builder()
                .paymentControl(PaymentControl.REQUESTED)
                .paymentRequestDate(LocalDateTime.now())
                .paymentExpirationDate(LocalDateTime.now().plusDays(7))
                .lastDigitsCreditCard(paymentDTO.creditCardNumber().substring(paymentDTO.creditCardNumber().length() - 4))
                .valuePaid(paymentDTO.valuePaid())
                .recurrence(true)
                .user(user)
                .build();
    }

    public CreditCard toCreditCardEntity(PaymentDTO paymentDTO, User user) {
        return CreditCard.builder()
                .cardHolderFullName(paymentDTO.cardHolderFullName())
                .cardHolderCpf(paymentDTO.cardHolderCpf())
                .creditCardNumber(paymentDTO.creditCardNumber())
                .expirationDate(paymentDTO.expirationDate())
                .cvvCode(paymentDTO.cvvCode())
                .user(user)
                .build();
    }

    public PaymentEventoDTO toEventoDTO(Payment payment) {
        return new PaymentEventoDTO(
                payment.getPaymentId(),
                payment.getPaymentControl().name(),
                payment.getPaymentRequestDate(),
                payment.getPaymentCompletionDate(),
                payment.getPaymentExpirationDate(),
                payment.getLastDigitsCreditCard(),
                payment.getValuePaid(),
                payment.getPaymentMessage(),
                payment.isRecurrence(),
                payment.getUser().getUserId()
        );
    }
}
