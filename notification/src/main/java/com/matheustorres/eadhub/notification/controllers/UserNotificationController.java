package com.matheustorres.eadhub.notification.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.matheustorres.eadhub.notification.configs.security.AuthenticationCurrentUserService;
import com.matheustorres.eadhub.notification.domain.models.Notification;
import com.matheustorres.eadhub.notification.dtos.NotificationDTO;
import com.matheustorres.eadhub.notification.services.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private final NotificationService notificationService;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<Notification>> getAllNotificationsByUser(@PathVariable(value = "userId") UUID userId,
                                                                             @PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                             Authentication authentication){
        validateUserAccess(userId);
        log.info("GET request /users/{}/notifications", userId);
        log.info("User {} authenticated", authentication.getName());
        Page<Notification> notificationPage = notificationService.findAllNotificationByUser(userId, pageable);
        if(!notificationPage.isEmpty()){
            for(Notification notification : notificationPage.toList()){
                notification.add(linkTo(methodOn(UserNotificationController.class).updateNotification(userId, notification.getNotificationId(), null)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(notificationPage);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDTO notificationDTO){
        validateUserAccess(userId);
        log.info("PUT request /users/{}/notifications/{}", userId, notificationId);
        Optional<Notification> notificationOptional = notificationService.findByNotificationIdAndUserId(notificationId, userId);
        if(notificationOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
        }

        notificationOptional.get().setNotificationStatus(notificationDTO.notificationStatus());
        notificationService.saveNotification(notificationOptional.get());
        
        notificationOptional.get().add(linkTo(methodOn(UserNotificationController.class).updateNotification(userId, notificationId, null)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(notificationOptional.get());
    }

    private void validateUserAccess(UUID userId) {
        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if (!currentUserId.equals(userId) && !authenticationCurrentUserService.getAuthentication().getAuthorities().toString().contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Forbidden");
        }
    }

}
