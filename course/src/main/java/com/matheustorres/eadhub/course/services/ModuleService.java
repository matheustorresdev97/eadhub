package com.matheustorres.eadhub.course.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.matheustorres.eadhub.course.domain.models.Module;
import com.matheustorres.eadhub.course.dtos.ModuleDTO;

public interface ModuleService {
    void delete(Module module);
    Module save(Module module);
    Optional<Module> findById(UUID moduleId);
    Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId);
    Page<Module> findAllByCourse(Specification<Module> spec, Pageable pageable);
    Module update(Module module, ModuleDTO moduleDto);
}
