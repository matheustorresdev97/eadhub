package com.matheustorres.eadhub.course.validation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.matheustorres.eadhub.course.clients.AuthUserClient;
import com.matheustorres.eadhub.course.domain.enums.UserType;
import com.matheustorres.eadhub.course.dtos.CourseDTO;
import com.matheustorres.eadhub.course.dtos.UserDTO;

import feign.FeignException;

@Component
public class CourseValidator implements Validator {

    private final Validator validator;
    private final AuthUserClient authUserClient;

    public CourseValidator(@Qualifier("defaultValidator") Validator validator, AuthUserClient authUserClient) {
        this.validator = validator;
        this.authUserClient = authUserClient;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CourseDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseDTO courseDto = (CourseDTO) o;
        validator.validate(courseDto, errors);
        if(!errors.hasErrors()){
            validaUserInstructor(courseDto.userInstructor(), errors);
        }
    }

    private void validaUserInstructor(UUID userInstructor, Errors errors) {
        UserDTO userDto;
        try{
            userDto = authUserClient.getUserById(userInstructor);
            if(userDto.userType().equals(UserType.STUDENT)){
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
            }
        } catch (FeignException.NotFound e){
            errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
        } catch (FeignException e) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Error communicating with AuthUser service.");
        }
    }

}
