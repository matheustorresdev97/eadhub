package com.matheustorres.eadhub.authuser.mappers;

import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;

@Component
public class UserMapper {

    public User.UserBuilder toEntityBuilder(UserDTO userDTO) {
        if (userDTO == null) {
            return User.builder();
        }

        return User.builder()
            .username(userDTO.username())
            .email(userDTO.email())
            .password(userDTO.password())
            .fullName(userDTO.fullName())
            .phoneNumber(userDTO.phoneNumber())
            .cpf(userDTO.cpf())
            .imageUrl(userDTO.imageUrl());
    }
}
