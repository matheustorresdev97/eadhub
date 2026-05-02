package com.matheustorres.eadhub.authuser.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.authuser.domain.enums.RoleType;
import com.matheustorres.eadhub.authuser.domain.models.Role;
import com.matheustorres.eadhub.authuser.repositories.RoleRepository;
import com.matheustorres.eadhub.authuser.services.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType);
    }
}
