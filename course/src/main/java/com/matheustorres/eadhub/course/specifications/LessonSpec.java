package com.matheustorres.eadhub.course.specifications;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.matheustorres.eadhub.course.domain.models.Lesson;
import com.matheustorres.eadhub.course.domain.models.Module;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public interface LessonSpec extends Specification<Lesson> {

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpecification extends Specification<Lesson> {}

    public static Specification<Lesson> lessonModuleId(final UUID moduleId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Lesson> lesson = root;
            Root<Module> module = query.from(Module.class);
            Expression<Collection<Lesson>> modulesLessons = module.get("lessons");
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, modulesLessons));
        };
    }
}
