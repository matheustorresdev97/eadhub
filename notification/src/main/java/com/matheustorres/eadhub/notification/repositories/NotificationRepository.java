package com.matheustorres.eadhub.notification.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheustorres.eadhub.notification.domain.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
