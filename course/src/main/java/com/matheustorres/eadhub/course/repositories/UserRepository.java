package com.matheustorres.eadhub.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheustorres.eadhub.course.domain.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
