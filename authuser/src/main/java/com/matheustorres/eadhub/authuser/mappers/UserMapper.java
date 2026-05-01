package com.matheustorres.eadhub.authuser.mappers;

import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.authuser.domain.enums.ActionType;
import com.matheustorres.eadhub.authuser.domain.models.User;
import com.matheustorres.eadhub.authuser.dtos.UserDTO;
import com.matheustorres.eadhub.authuser.dtos.UserEventDTO;

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

    public UserEventDTO toEventDTO(User user, ActionType actionType) {
        return new UserEventDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getUserStatus().name(),
                user.getUserType().name(),
                user.getPhoneNumber(),
                user.getCpf(),
                user.getImageUrl(),
                actionType.name());
    }
}
