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
    @Spec(path = "email", spec = Like.class)
})
public interface UserSpec extends Specification<User> {}
