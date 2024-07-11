package com.example.momcare.handler;

import com.example.momcare.models.Notification;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.NotificationHandlerRequest;
import com.example.momcare.payload.request.UserHandlerRequest;
import com.example.momcare.payload.response.NotificationResponse;
import com.example.momcare.repository.NotificationRepository;
import com.example.momcare.service.NotificationService;
import com.example.momcare.service.UserService;
import com.example.momcare.util.Constant;
import com.example.momcare.util.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationHandler extends TextWebSocketHandler {
    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private static final long HEARTBEAT_INTERVAL = 30L * 1000;

    public NotificationHandler(UserService userService, NotificationRepository notificationRepository, NotificationService notificationService) {
        this.userService = userService;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Store the session
        sessions.put(session.getId(), session);
        startHeartbeat(session);
    }
    private void startHeartbeat(WebSocketSession session) {
        Runnable heartbeatTask = () -> {
            while (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage("{\"type\":\"heartbeat\"}"));
                    Thread.sleep(HEARTBEAT_INTERVAL);
                } catch (InterruptedException | IOException e) {
                    // Handle exception
                    break;
                }
            }
        };
        Thread heartbeatThread = new Thread(heartbeatTask);
        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the session when it's closed
        sessions.remove(session.getId());
    }

    // Method to send message to a specific session
    public void sendMessageToSession(String sessionId, TextMessage message, String receiverId) throws IOException {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        } else {
            // Handle the case when the session is not open
            User user = userService.findAccountByID(receiverId);
            if (user != null) {
                List<NotificationResponse> notificationsMissed = user.getNotificationsMissed();
                if (notificationsMissed == null) {
                    notificationsMissed = new ArrayList<>();
                }
                NotificationResponse notification = objectMapper.readValue(message.getPayload(), NotificationResponse.class);
                notification.setRead(false);
                notificationsMissed.add(notification);
                user.setNotificationsMissed(notificationsMissed);
                userService.update(user);
            }
        }
    }

    @Override
    @Transactional
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // Handle incoming message
        if (isMessageUserId(message)) {
            // Process message of type A
            UserHandlerRequest userHandlerRequest = objectMapper.readValue((String) message.getPayload(), UserHandlerRequest.class);
            if (!Validator.isValidHexString(userHandlerRequest.getId())) {
                sendMessageToSession(session.getId(), new TextMessage("Invalid user ID!"), null);
                return;
            }
            User user = userService.findAccountByID(userHandlerRequest.getId());
            if (user != null) {
                // Send a message to the user
                user.setSessionId(session.getId());
                if (user.getNotificationsMissed() != null && !user.getNotificationsMissed().isEmpty()) {
                    sendMessageToSession(session.getId(), new TextMessage(objectMapper.writeValueAsString(user.getNotificationsMissed())), user.getId());
                    user.setNotificationsMissed(new ArrayList<>());
                } else {
                    sendMessageToSession(session.getId(), new TextMessage("User found!"), user.getId());
                }
                userService.update(user);
            } else {
                sendMessageToSession(session.getId(), new TextMessage(Constant.USER_NOT_FOUND), null);
            }
        } else if (isMessageNotification(message)) {
            // Process message of type B
            NotificationHandlerRequest notificationHandlerRequest = objectMapper.readValue((String) message.getPayload(), NotificationHandlerRequest.class);
            User user = userService.findAccountByID(notificationHandlerRequest.getReceiverId());
            if (user != null) {
                // Send a message to the user
                Notification notification = map(notificationHandlerRequest);
                notificationRepository.save(notification);
                try {
                    NotificationResponse notificationResponse = notificationService.map(notification);
                    sendMessageToSession(user.getSessionId(), new TextMessage(objectMapper.writeValueAsString(notificationResponse)), user.getId());
                } catch (Exception e) {
                    sendMessageToSession(session.getId(), new TextMessage(Constant.USER_NOT_FOUND), null);
                }
            } else {
                sendMessageToSession(session.getId(), new TextMessage(Constant.USER_NOT_FOUND), null);
            }
        } else {
            // Handle unknown message type or throw an error
            sendMessageToSession(session.getId(), new TextMessage("Invalid input!"), null);
        }

    }

    private boolean isMessageUserId(WebSocketMessage<?> message) {
        Object payload = message.getPayload();
        if (payload instanceof String) {
            String jsonString = (String) payload;
            try {
                // Deserialize JSON payload into UserHandlerRequest object
                UserHandlerRequest userHandlerRequest = objectMapper.readValue(jsonString, UserHandlerRequest.class);
                // Check if deserialization was successful and if the 'id' field is not null or empty
                return userHandlerRequest != null && userHandlerRequest.getId() != null && !userHandlerRequest.getId().isEmpty();
            } catch (Exception ex) {
                // Handle JSON parsing or deserialization errors
                // For example, log an error or send an error response
                return false;
            }
        }

        return false; // Return false if the payload is not a String or if deserialization fails
    }

    private boolean isMessageNotification(WebSocketMessage<?> message) {
        Object payload = message.getPayload();
        if (payload instanceof String) {
            String jsonString = (String) payload;
            try {
                // Deserialize JSON payload into UserHandlerRequest object
                NotificationHandlerRequest notificationHandlerRequest = objectMapper.readValue(jsonString, NotificationHandlerRequest.class);
                // Check if deserialization was successful and if the 'id' field is not null or empty
                return notificationHandlerRequest != null;
            } catch (Exception ex) {
                // Handle JSON parsing or deserialization errors
                // For example, log an error or send an error response
                return false;
            }
        }

        return false; // Return false if the payload is not a String or if deserialization fails
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public Notification map(NotificationHandlerRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setReceiverId(notificationRequest.getReceiverId());
        notification.setSenderId(notificationRequest.getSenderId());
        notification.setTimestamp(LocalDateTime.now().toString());
        notification.setRead(false);
        notification.setNotificationType(notificationRequest.getNotificationType());
        notification.setTargetId(notificationRequest.getTargetId());
        return notification;
    }
}
