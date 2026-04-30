package com.matheustorres.eadhub.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheustorres.eadhub.course.domain.models.Module;

public interface ModuleRepository extends JpaRepository<Module, UUID>, JpaSpecificationExecutor<Module> {

    @EntityGraph(attributePaths = { "course" })
    Module findByTitle(String title);

    @Query(value = "select m from Module m where m.course.courseId = :courseId order by m.creationDate asc")
    Page<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId, Pageable pageable);

    @Query(value = "select m from Module m where m.course.courseId = :courseId")
    List<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "select m from Module m where m.course.courseId = :courseId and m.moduleId = :moduleId")
    Optional<Module> findModuleIntoCourse(@Param("moduleId") UUID moduleId, @Param("courseId") UUID courseId);
}
