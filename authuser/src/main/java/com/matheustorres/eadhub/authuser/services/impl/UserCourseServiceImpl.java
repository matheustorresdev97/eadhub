package com.matheustorres.eadhub.authuser.services.impl;

import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.authuser.repositories.UserCourseRepository;
import com.matheustorres.eadhub.authuser.services.UserCourseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository userCourseRepository;
}
