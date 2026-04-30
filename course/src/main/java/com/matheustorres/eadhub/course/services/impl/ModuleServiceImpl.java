package com.matheustorres.eadhub.course.services.impl;

import org.springframework.stereotype.Service;
import com.matheustorres.eadhub.course.repositories.ModuleRepository;
import com.matheustorres.eadhub.course.services.ModuleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
}
