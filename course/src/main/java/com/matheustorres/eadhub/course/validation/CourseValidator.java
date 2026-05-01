package com.matheustorres.eadhub.course.validation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.matheustorres.eadhub.course.domain.enums.UserType;
import com.matheustorres.eadhub.course.domain.models.User;
import com.matheustorres.eadhub.course.dtos.CourseDTO;
import com.matheustorres.eadhub.course.services.UserService;

@Component
public class CourseValidator implements Validator {

    private final Validator validator;
    private final UserService userService;

    public CourseValidator(@Qualifier("defaultValidator") Validator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CourseDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseDTO courseDto = (CourseDTO) o;
        validator.validate(courseDto, errors);
        if (!errors.hasErrors()) {
            validaUserInstructor(courseDto.userInstructor(), errors);
        }
    }

    private void validaUserInstructor(UUID userInstructor, Errors errors) {
        Optional<User> optUser = userService.findByInstructorId(userInstructor);
        if (!optUser.isPresent()) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
        }
        if (optUser.get().getUserType().equals(UserType.STUDENT)) {
            errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
        }
    }

}
