package com.matheustorres.eadhub.payment.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matheustorres.eadhub.payment.domain.enums.PaymentControl;
import com.matheustorres.eadhub.payment.domain.enums.PaymentStatus;
import com.matheustorres.eadhub.payment.domain.models.CreditCard;
import com.matheustorres.eadhub.payment.domain.models.Payment;
import com.matheustorres.eadhub.payment.domain.models.User;
import com.matheustorres.eadhub.payment.dtos.PaymentCommandDTO;
import com.matheustorres.eadhub.payment.dtos.PaymentDTO;
import com.matheustorres.eadhub.payment.mappers.PaymentMapper;
import com.matheustorres.eadhub.payment.publishers.PaymentCommandPublisher;
import com.matheustorres.eadhub.payment.publishers.PaymentEventPublisher;
import com.matheustorres.eadhub.payment.repositories.CreditCardRepository;
import com.matheustorres.eadhub.payment.repositories.PaymentRepository;
import com.matheustorres.eadhub.payment.repositories.UserRepository;
import com.matheustorres.eadhub.payment.services.PaymentService;
import com.matheustorres.eadhub.payment.services.PaymentStipeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CreditCardRepository creditCardRepository;
    private final UserRepository userRepository;
    private final PaymentStipeService paymentStipeService;
    private final PaymentCommandPublisher paymentCommandPublisher;
    private final PaymentEventPublisher paymentEventPublisher;
    private final PaymentMapper paymentMapper;

    @Transactional
    @Override
    public Payment requestPayment(PaymentDTO paymentDTO, User user) {
        var payment = paymentMapper.toPaymentEntity(paymentDTO, user);
        payment = paymentRepository.save(payment);

        var creditCardOptional = creditCardRepository.findByUser(user);
        CreditCard creditCard;
        if (creditCardOptional.isPresent()) {
            creditCard = creditCardOptional.get().toBuilder()
                    .cardHolderFullName(paymentDTO.cardHolderFullName())
                    .cardHolderCpf(paymentDTO.cardHolderCpf())
                    .creditCardNumber(paymentDTO.creditCardNumber())
                    .expirationDate(paymentDTO.expirationDate())
                    .cvvCode(paymentDTO.cvvCode())
                    .build();
        } else {
            creditCard = paymentMapper.toCreditCardEntity(paymentDTO, user);
        }
        creditCard = creditCardRepository.save(creditCard);

        var paymentCommandDTO = new PaymentCommandDTO(user.getUserId(), payment.getPaymentId(), creditCard.getCardId());
        paymentCommandPublisher.publishPaymentCommand(paymentCommandDTO);

        return payment;
    }

    @Override
    public Optional<Payment> findLastPaymentByUser(User user) {
        return paymentRepository.findTopByUserOrderByPaymentRequestDateDesc(user);
    }

    @Override
    public Page<Payment> findAllByUser(Specification<Payment> spec, Pageable pageable) {
        return paymentRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<Payment> findPaymentByUser(UUID userId, UUID paymentId) {
        return paymentRepository.findPaymentByUser(userId, paymentId);
    }

    @Transactional
    @Override
    public void makePayment(PaymentCommandDTO paymentCommandDTO) {
        var payment = paymentRepository.findById(paymentCommandDTO.paymentId()).get();
        var creditCard = creditCardRepository.findById(paymentCommandDTO.cardId()).get();

        payment = paymentStipeService.processStripePayment(payment, creditCard);

        paymentRepository.save(payment);

        if (payment.getPaymentControl().equals(PaymentControl.EFFECTED)) {
            var user = payment.getUser();
            user.setPaymentStatus(PaymentStatus.PAYING);
            user.setLastPaymentDate(LocalDateTime.now());
            user.setPaymentExpirationDate(LocalDateTime.now().plusMonths(1));
            if (user.getFirstPaymentDate() == null) {
                user.setFirstPaymentDate(LocalDateTime.now());
            }
            userRepository.save(user);
        }

        var paymentEventoDTO = paymentMapper.toEventoDTO(payment);
        paymentEventPublisher.publishPaymentEvent(paymentEventoDTO);
    }
}
