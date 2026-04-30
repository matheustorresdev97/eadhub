package com.matheustorres.eadhub.course.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.matheustorres.eadhub.course.domain.models.Lesson;
import com.matheustorres.eadhub.course.dtos.LessonDTO;
import com.matheustorres.eadhub.course.repositories.LessonRepository;
import com.matheustorres.eadhub.course.services.LessonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    @Override
    public Lesson update(Lesson lesson, LessonDTO lessonDto) {
        lesson.updateLesson(lessonDto.name(), lessonDto.description(), lessonDto.videoUrl());
        return lessonRepository.save(lesson);
    }

    @Override
    public Page<Lesson> findAllByModule(UUID moduleId, Pageable pageable) {
        return lessonRepository.findAllLessonsIntoModule(moduleId, pageable);
    }

    @Override
    public Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }
}
