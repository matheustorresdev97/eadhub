package com.matheustorres.eadhub.authuser.specifications;


import org.springframework.data.jpa.domain.Specification;
import com.matheustorres.eadhub.authuser.domain.models.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@And({
        @Spec(path = "userType", spec = Equal.class),
        @Spec(path = "userStatus", spec = Equal.class),
        @Spec(path = "email", spec = Like.class),
        @Spec(path = "fullName", spec = Like.class)
})

public interface UserSpec extends Specification<User> {

    // public static Specification<User> userCourseId(final UUID courseId) {
    // return (root, query, cb) -> {
    // query.distinct(true);
    // Join<User, UserCourse> userProd = root.join("usersCourses");
    // return cb.equal(userProd.get("courseId"), courseId);
    // };
    // }
}
