package com.matheustorres.eadhub.course.mappers;

import com.matheustorres.eadhub.course.domain.models.Course;
import com.matheustorres.eadhub.course.domain.models.User;
import com.matheustorres.eadhub.course.dtos.NotificationCommandDTO;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationCommandDTO toNotificationCommandDTO(Course course, User user) {
        String title = "Bem-vindo(a) ao Curso: " + course.getName();
        String message = user.getFullName() + " a sua inscrição foi realizada com sucesso!";
        return new NotificationCommandDTO(user.getUserId(), title, message);
    }
}
