package com.matheustorres.eadhub.payment.mappers;

import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.payment.domain.models.User;
import com.matheustorres.eadhub.payment.dtos.UserEventDTO;
import com.matheustorres.eadhub.payment.domain.enums.PaymentStatus;

@Component
public class UserMapper {

    public User toEntity(UserEventDTO userEventDTO) {
        return User.builder()
                .userId(userEventDTO.userId())
                .email(userEventDTO.email())
                .fullName(userEventDTO.fullName())
                .userStatus(userEventDTO.userStatus())
                .userType(userEventDTO.userType())
                .cpf(userEventDTO.cpf())
                .phoneNumber(userEventDTO.phoneNumber())
                .paymentStatus(PaymentStatus.NOTSTARTED) // Default status for new users in payment service
                .build();
    }
}
