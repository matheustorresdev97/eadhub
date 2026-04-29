package com.matheustorres.eadhub.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonView;

public record UserDTO(
        @JsonView(UserView.RegistrationPost.class)
        String username,

        @JsonView(UserView.RegistrationPost.class)
        String email,

        @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
        String password,

        @JsonView(UserView.PasswordPut.class)
        String oldPassword,

        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        String fullName,

        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        String phoneNumber,

        @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
        String cpf,

        @JsonView({UserView.RegistrationPost.class, UserView.ImagePut.class})
        String imageUrl) {

    public interface UserView {
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface PasswordPut {}
        public static interface ImagePut {}
    }
}
