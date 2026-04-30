package com.matheustorres.eadhub.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matheustorres.eadhub.course.domain.models.Lesson;
import com.matheustorres.eadhub.course.domain.models.Module;
import com.matheustorres.eadhub.course.dtos.ModuleDTO;
import com.matheustorres.eadhub.course.repositories.LessonRepository;
import com.matheustorres.eadhub.course.repositories.ModuleRepository;
import com.matheustorres.eadhub.course.services.ModuleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(Module module) {
        List<Lesson> lessonList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
        if (!lessonList.isEmpty()) {
            lessonRepository.deleteAll(lessonList);
        }
        moduleRepository.delete(module);
    }

    @Override
    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public Optional<Module> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }

    @Override
    public Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(moduleId, courseId);
    }

    @Override
    public Page<Module> findAllByCourse(UUID courseId, Pageable pageable) {
        return moduleRepository.findAllModulesIntoCourse(courseId, pageable);
    }

    @Override
    public Module update(Module module, ModuleDTO moduleDto) {
        module.updateModule(moduleDto.title(), moduleDto.description());
        return moduleRepository.save(module);
    }
}
