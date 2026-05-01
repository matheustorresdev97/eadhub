package com.matheustorres.eadhub.course.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.course.domain.enums.ActionType;
import com.matheustorres.eadhub.course.dtos.UserEventDTO;
import com.matheustorres.eadhub.course.mappers.UserMapper;
import com.matheustorres.eadhub.course.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserService userService;
    private final UserMapper userMapper;

    @RabbitListener(queues = "${eadhub.broker.queue.userEvent}")
    public void listenUserEvent(@Payload UserEventDTO userEventDto) {
        var actionType = ActionType.valueOf(userEventDto.actionType());
        var user = userMapper.toEntity(userEventDto);

        switch (actionType) {
            case CREATE, UPDATE -> userService.save(user);
            case DELETE -> userService.delete(user.getUserId());
        }
    }
}
