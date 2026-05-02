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

import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.domain.models.User;
import com.matheustorres.eadhub.course.domain.models.Lesson;
import com.matheustorres.eadhub.course.domain.models.Module;
import com.matheustorres.eadhub.course.dtos.CourseDTO;
import com.matheustorres.eadhub.course.mappers.CourseMapper;
import com.matheustorres.eadhub.course.mappers.NotificationMapper;
import com.matheustorres.eadhub.course.publishers.NotificationCommandPublisher;
import com.matheustorres.eadhub.course.repositories.CourseRepository;
import com.matheustorres.eadhub.course.repositories.LessonRepository;
import com.matheustorres.eadhub.course.repositories.ModuleRepository;
import com.matheustorres.eadhub.course.services.CourseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final CourseMapper courseMapper;
    private final NotificationMapper notificationMapper;
    private final NotificationCommandPublisher notificationCommandPublisher;

    @Override
    public boolean existsByCourseId(UUID courseId) {
        return courseRepository.existsById(courseId);
    }

    @Override
    @Transactional
    public void delete(Course course) {
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
        courseRepository.deleteCourseUserByCourse(course.getCourseId());
        courseRepository.delete(course);
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

    @Override
    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
        return courseRepository.existsByCourseAndUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        courseRepository.saveCourseUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveAndSubscriptionUserInCourseAndSendNotification(Course course, User user){
        courseRepository.saveCourseUser(course.getCourseId(), user.getUserId());
        try {
            var notificationCommandDto = notificationMapper.toNotificationCommandDTO(course, user);
            notificationCommandPublisher.publishNotificationCommand(notificationCommandDto);
        } catch (Exception e){
            log.warn("Error sending notification!");
        }
    }

}   
