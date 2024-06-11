package com.example.momcare.repository;

import com.example.momcare.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    Page<Notification> findByReceiverIdAndIsRead(String receiverId, boolean isRead, Pageable pageable);
    Page<Notification> findByReceiverId(String receiverId, Pageable pageable);
}
