package com.matheustorres.eadhub.authuser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheustorres.eadhub.authuser.domain.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
