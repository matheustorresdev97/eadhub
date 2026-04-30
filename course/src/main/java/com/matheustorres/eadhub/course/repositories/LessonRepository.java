package com.matheustorres.eadhub.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheustorres.eadhub.course.domain.models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query(value = "select l from Lesson l where l.module.moduleId = :moduleId order by l.creationDate asc")
    List<Lesson> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);

    @Query(value = "select l from Lesson l where l.module.moduleId = :moduleId order by l.creationDate asc")
    Page<Lesson> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId, Pageable pageable);

    @Query(value = "select l from Lesson l where l.module.moduleId = :moduleId and l.lessonId = :lessonId")
    Optional<Lesson> findLessonIntoModule(@Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);
}
