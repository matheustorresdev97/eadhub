package com.matheustorres.eadhub.payment.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.eadhub.payment.domain.models.Payment;
import com.matheustorres.eadhub.payment.domain.models.User;
import com.matheustorres.eadhub.payment.dtos.PaymentDTO;
import com.matheustorres.eadhub.payment.services.PaymentService;
import com.matheustorres.eadhub.payment.services.UserService;
import com.matheustorres.eadhub.payment.specifications.PaymentSpec;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/users/{userId}/payments")
    public ResponseEntity<Object> requestPayment(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody @Valid PaymentDTO paymentDTO) {
        log.debug("POST requestPayment paymentDTO received {}", paymentDTO.toString());
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        var payment = paymentService.requestPayment(paymentDTO, userOptional.get());
        log.debug("POST requestPayment paymentId saved {}", payment.getPaymentId());
        log.info("Payment requested successfully paymentId {}", payment.getPaymentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{userId}/payments")
    public ResponseEntity<Page<Payment>> getAllPayments(@PathVariable(value = "userId") UUID userId,
                                                        PaymentSpec spec,
                                                        @PageableDefault(page = 0, size = 10, sort = "paymentRequestDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.findAllByUser(PaymentSpec.paymentUserId(userId).and(spec), pageable));
    }
}
