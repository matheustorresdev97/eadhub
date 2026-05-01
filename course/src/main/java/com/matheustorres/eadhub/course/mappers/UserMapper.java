package com.matheustorres.eadhub.course.mappers;

import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.course.domain.models.User;
import com.matheustorres.eadhub.course.dtos.UserEventDTO;

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
                .imageUrl(userEventDTO.imageUrl())
                .build();
    }
}
