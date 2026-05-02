package com.matheustorres.eadhub.authuser.dtos;

import lombok.NonNull;

public record JwtDTO(@NonNull String token, String type) {
    public JwtDTO(String token) {
        this(token, "Bearer");
    }
}
