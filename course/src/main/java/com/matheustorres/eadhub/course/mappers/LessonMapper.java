package com.matheustorres.eadhub.course.mappers;

import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.course.domain.models.Lesson;
import com.matheustorres.eadhub.course.dtos.LessonDTO;

@Component
public class LessonMapper {

    public Lesson toEntityBuilder(LessonDTO lessonDto) {
        return Lesson.builder()
                .title(lessonDto.name())
                .description(lessonDto.description())
                .videoUrl(lessonDto.videoUrl())
                .build();
    }
}
