package com.matheustorres.eadhub.course.specifications;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.domain.models.User;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@And({
        @Spec(path = "email", spec = Like.class),
        @Spec(path = "fullName", spec = Like.class),
        @Spec(path = "userStatus", spec = Equal.class),
        @Spec(path = "userType", spec = Equal.class)
})
public interface UserSpec extends Specification<User> {

    public static Specification<User> userCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<User> user = root;
            Root<Course> course = query.from(Course.class);
            Expression<Collection<User>> coursesUsers = course.get("users");
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(user, coursesUsers));
        };
    }
}
