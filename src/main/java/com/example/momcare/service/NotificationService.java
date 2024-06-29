package com.example.momcare.service;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.Notification;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.NotificationRequest;
import com.example.momcare.payload.response.NotificationResponse;
import com.example.momcare.repository.NotificationRepository;
import com.example.momcare.repository.UserRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<Notification> save(NotificationRequest notificationRequest) {
        Notification notification = map(notificationRequest);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(this.notificationRepository.save(notification));
        return notifications;
    }

    private Notification map(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setReceiverId(notificationRequest.getReceiverId());
        notification.setSenderId(notificationRequest.getSenderId());
        notification.setTimestamp(notificationRequest.getTimestamp());
        notification.setRead(false);
        notification.setNotificationType(notificationRequest.getNotificationType());
        notification.setTargetId(notificationRequest.getTargetId());
        return notification;
    }

    public NotificationResponse map(Notification notification) throws ResourceNotFoundException {
        User user = this.userRepository.findById(notification.getSenderId()).orElseThrow(() -> new ResourceNotFoundException(Constant.USER_NOT_FOUND));
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setId(notification.getId());
        notificationResponse.setReceiverId(notification.getReceiverId());
        notificationResponse.setSenderId(notification.getSenderId());
        notificationResponse.setTimestamp(notification.getTimestamp());
        notificationResponse.setRead(notification.isRead());
        notificationResponse.setNotificationType(notification.getNotificationType());
        notificationResponse.setTargetId(notification.getTargetId());
        notificationResponse.setSenderAvt(user.getAvtUrl());
        notificationResponse.setSenderName(user.getNameDisplay());
        return notificationResponse;
    }


    @Transactional
    public List<NotificationResponse> markAsRead(String id) throws ResourceNotFoundException {
        Notification notification = this.notificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND));
        notification.markAsRead();
        this.notificationRepository.save(notification);
        return List.of(this.map(notification));
    }

    public List<NotificationResponse> getAllByReceiverId(String receiverId, int page, int size) throws ResourceNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationsPage = notificationRepository.findByReceiverId(receiverId, pageable);
        List <Notification> notifications = notificationsPage.getContent();
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationResponses.add(this.map(notification));
        }
        return notificationResponses;

    }

    public List<NotificationResponse> getUnreadNotifications(String receiverId, int page, int size) throws ResourceNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationsPage = notificationRepository.findByReceiverIdAndIsRead(receiverId, false, pageable);
        List <Notification> notifications = notificationsPage.getContent();
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationResponses.add(this.map(notification));
        }
        return notificationResponses;
    }

    @Transactional
    public void deleteNotification(String id) throws ResourceNotFoundException {
        Notification notification = this.notificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        this.notificationRepository.delete(notification);
    }


}
