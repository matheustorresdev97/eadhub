package com.matheustorres.eadhub.authuser.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheustorres.eadhub.authuser.domain.enums.RoleType;
import com.matheustorres.eadhub.authuser.domain.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleName(RoleType name);
}
