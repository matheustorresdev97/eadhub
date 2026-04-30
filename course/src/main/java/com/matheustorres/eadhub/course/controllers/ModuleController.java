package com.matheustorres.eadhub.course.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.domain.models.Module;
import com.matheustorres.eadhub.course.dtos.ModuleDTO;
import com.matheustorres.eadhub.course.mappers.ModuleMapper;
import com.matheustorres.eadhub.course.services.CourseService;
import com.matheustorres.eadhub.course.services.ModuleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses/{courseId}/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;
    private final CourseService courseService;
    private final ModuleMapper moduleMapper;

    @PostMapping
    public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
                                            @RequestBody @Valid ModuleDTO moduleDto) {
        Optional<Course> courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        var module = moduleMapper.toEntityBuilder(moduleDto);
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setCourse(courseOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module));
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable(value = "courseId") UUID courseId,
                                              @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }
        moduleService.delete(moduleOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully.");
    }

    @PutMapping("/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId") UUID courseId,
                                              @PathVariable(value = "moduleId") UUID moduleId,
                                              @RequestBody @Valid ModuleDTO moduleDto) {
        Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.update(moduleOptional.get(), moduleDto));
    }

    @GetMapping
    public ResponseEntity<Page<Module>> getAllModules(@PathVariable(value = "courseId") UUID courseId,
                                                     @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Module> modulePage = moduleService.findAllByCourse(courseId, pageable);
        if (!modulePage.isEmpty()) {
            for (Module module : modulePage) {
                module.add(linkTo(methodOn(ModuleController.class).getModuleById(courseId, module.getModuleId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(modulePage);
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<Object> getModuleById(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<Module> moduleOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }
        moduleOptional.get().add(linkTo(methodOn(ModuleController.class).getModuleById(courseId, moduleId)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(moduleOptional.get());
    }
}
