package com.matheustorres.eadhub.course.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.matheustorres.eadhub.course.domain.models.Lesson;
import com.matheustorres.eadhub.course.dtos.LessonDTO;

public interface LessonService {
    Lesson save(Lesson lesson);
    void delete(Lesson lesson);
    Lesson update(Lesson lesson, LessonDTO lessonDto);
    Page<Lesson> findAllByModule(Specification<Lesson> spec, Pageable pageable);
    Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId);
}
