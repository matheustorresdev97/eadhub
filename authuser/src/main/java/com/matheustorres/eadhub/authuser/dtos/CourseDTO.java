package com.matheustorres.eadhub.authuser.dtos;

import java.util.UUID;
import com.matheustorres.eadhub.authuser.domain.enums.CourseLevel;
import com.matheustorres.eadhub.authuser.domain.enums.CourseStatus;

public record CourseDTO(UUID courseId, String name, String description, String imageUrl,
        CourseLevel level, CourseStatus status, UUID instructorId) {

}
