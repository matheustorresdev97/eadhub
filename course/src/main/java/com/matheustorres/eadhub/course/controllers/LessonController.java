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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.eadhub.course.domain.models.Lesson;
import com.matheustorres.eadhub.course.domain.models.Module;
import com.matheustorres.eadhub.course.dtos.LessonDTO;
import com.matheustorres.eadhub.course.mappers.LessonMapper;
import com.matheustorres.eadhub.course.services.LessonService;
import com.matheustorres.eadhub.course.services.ModuleService;
import com.matheustorres.eadhub.course.specifications.LessonSpec;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/modules/{moduleId}/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final ModuleService moduleService;
    private final LessonMapper lessonMapper;

    @PostMapping
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                            @RequestBody @Valid LessonDTO lessonDto) {
        Optional<Module> moduleOptional = moduleService.findById(moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found.");
        }
        var lesson = lessonMapper.toEntityBuilder(lessonDto);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setModule(moduleOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                              @PathVariable(value = "lessonId") UUID lessonId) {
        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        lessonService.delete(lessonOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully.");
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                              @PathVariable(value = "lessonId") UUID lessonId,
                                              @RequestBody @Valid LessonDTO lessonDto) {
        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.update(lessonOptional.get(), lessonDto));
    }

    @GetMapping
    public ResponseEntity<Page<Lesson>> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId,
                                                     LessonSpec.LessonSpecification spec,
                                                     @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Lesson> lessonPage = lessonService.findAllByModule(LessonSpec.lessonModuleId(moduleId).and(spec), pageable);
        if (!lessonPage.isEmpty()) {
            for (Lesson lesson : lessonPage) {
                lesson.add(linkTo(methodOn(LessonController.class).getLessonById(moduleId, lesson.getLessonId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(lessonPage);
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Object> getLessonById(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId) {
        Optional<Lesson> lessonOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }
        lessonOptional.get().add(linkTo(methodOn(LessonController.class).getLessonById(moduleId, lessonId)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(lessonOptional.get());
    }
}
