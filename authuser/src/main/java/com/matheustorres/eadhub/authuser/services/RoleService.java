package com.matheustorres.eadhub.authuser.services;

import java.util.Optional;

import com.matheustorres.eadhub.authuser.domain.enums.RoleType;
import com.matheustorres.eadhub.authuser.domain.models.Role;

public interface RoleService {

    Optional<Role> findByRoleName(RoleType roleType);
}
