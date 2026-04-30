package com.matheustorres.eadhub.course.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheustorres.eadhub.course.domain.models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query(value = "select l from Lesson l where l.module.moduleId = :moduleId order by l.creationDate asc")
    List<Lesson> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);
}
