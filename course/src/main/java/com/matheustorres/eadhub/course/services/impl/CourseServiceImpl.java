package com.matheustorres.eadhub.course.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.matheustorres.eadhub.course.clients.AuthUserClient;
import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.domain.models.CourseUser;
import com.matheustorres.eadhub.course.domain.models.Lesson;
import com.matheustorres.eadhub.course.domain.models.Module;
import com.matheustorres.eadhub.course.dtos.CourseDTO;
import com.matheustorres.eadhub.course.mappers.CourseMapper;
import com.matheustorres.eadhub.course.repositories.CourseRepository;
import com.matheustorres.eadhub.course.repositories.CourseUserRepository;
import com.matheustorres.eadhub.course.repositories.LessonRepository;
import com.matheustorres.eadhub.course.repositories.ModuleRepository;
import com.matheustorres.eadhub.course.services.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final CourseUserRepository courseUserRepository;
    private final AuthUserClient authUserClient;
    private final CourseMapper courseMapper;

    @Override
    public boolean existsByCourseId(UUID courseId) {
        return courseRepository.existsById(courseId);
    }

    @Override
    @Transactional
    public void delete(Course course) {
        boolean deleteCourseInAuthUser = false;
        List<Module> moduleList = moduleRepository.findAllModulesIntoCourse(course.getCourseId());
        if (!moduleList.isEmpty()) {
            for (Module module : moduleList) {
                List<Lesson> lessonList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessonList.isEmpty()) {
                    lessonRepository.deleteAll(lessonList);
                }
            }
            moduleRepository.deleteAll(moduleList);
        }
        List<CourseUser> courseUserList = courseUserRepository.findAllCourseUserIntoCourse(course.getCourseId());
        if (!courseUserList.isEmpty()) {
            courseUserRepository.deleteAll(courseUserList);
            deleteCourseInAuthUser = true;
        }
        courseRepository.delete(course);
        if (deleteCourseInAuthUser) {
            authUserClient.deleteCourseInAuthUser(course.getCourseId());
        }
    }

    @Override
    public Page<Course> findAll(Specification<Course> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<Course> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Course save(CourseDTO courseDto) {
        Course course = courseMapper.toEntityBuilder(courseDto);
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(course);
    }

    @Override
    public Course update(UUID courseId, CourseDTO courseDto) {
        Course course = findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found."));
        course.updateCourse(courseDto.name(), courseDto.description(), courseDto.imageUrl(), courseDto.courseStatus(),
                courseDto.courseLevel());
        return courseRepository.save(course);
    }
}
