package com.matheustorres.eadhub.payment.services;

import java.util.Optional;
import java.util.UUID;
import com.matheustorres.eadhub.payment.domain.models.User;

public interface UserService {
    User save(User user);

    void delete(UUID userId);

    Optional<User> findById(UUID userId);
}
