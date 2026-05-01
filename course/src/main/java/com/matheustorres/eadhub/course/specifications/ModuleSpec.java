package com.matheustorres.eadhub.course.specifications;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.domain.models.Module;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public interface ModuleSpec extends Specification<Module> {

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpecification extends Specification<Module> {}

    public static Specification<Module> moduleCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Module> module = root;
            Root<Course> course = query.from(Course.class);
            Expression<Collection<Module>> coursesModules = course.get("modules");
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, coursesModules));
        };
    }
}
