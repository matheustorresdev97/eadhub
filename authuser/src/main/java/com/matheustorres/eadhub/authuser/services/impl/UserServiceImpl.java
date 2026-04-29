package com.matheustorres.eadhub.authuser.services.impl;

import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.authuser.repositories.UserRepository;
import com.matheustorres.eadhub.authuser.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

}
