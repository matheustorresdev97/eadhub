package com.matheustorres.eadhub.course.dtos;

import java.util.UUID;

import com.matheustorres.eadhub.course.domain.enums.UserStatus;
import com.matheustorres.eadhub.course.domain.enums.UserType;

public record UserDTO(
        UUID userId,
        String username,
        String email,
        String fullName,
        UserStatus userStatus,
        UserType userType,
        String phoneNumber,
        String cpf,
        String imageUrl) {

}
