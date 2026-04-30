package com.matheustorres.eadhub.course.mappers;

import org.springframework.stereotype.Component;
import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.dtos.CourseDTO;

@Component
public class CourseMapper {

    public Course toEntityBuilder(CourseDTO courseDto) {
        return Course.builder()
                .name(courseDto.name())
                .description(courseDto.description())
                .imageUrl(courseDto.imageUrl())
                .courseStatus(courseDto.courseStatus())
                .courseLevel(courseDto.courseLevel())
                .userInstructor(courseDto.userInstructor())
                .build();
    }
}
