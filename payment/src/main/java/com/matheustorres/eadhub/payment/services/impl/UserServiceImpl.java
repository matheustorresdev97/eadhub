package com.matheustorres.eadhub.payment.services.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.matheustorres.eadhub.payment.domain.models.User;
import com.matheustorres.eadhub.payment.repositories.UserRepository;
import com.matheustorres.eadhub.payment.services.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }
}
