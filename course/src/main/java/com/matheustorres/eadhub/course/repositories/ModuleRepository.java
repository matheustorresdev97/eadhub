package com.matheustorres.eadhub.course.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheustorres.eadhub.course.domain.models.Module;

public interface ModuleRepository extends JpaRepository<Module, UUID> {

    @EntityGraph(attributePaths = { "course" })
    Module findByTitle(String title);

    @Query(value = "select m from Module m where m.course.courseId = :courseId order by m.creationDate asc")
    List<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId);
}
