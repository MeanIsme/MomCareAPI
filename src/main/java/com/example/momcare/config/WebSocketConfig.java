package com.example.momcare.config;

import com.example.momcare.handler.NotificationHandler;
import com.example.momcare.handler.TutorialHandler;
import com.example.momcare.repository.NotificationRepository;
import com.example.momcare.service.NotificationService;
import com.example.momcare.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final UserService userService;

    private final NotificationRepository notificationRepository;

    private final NotificationService notificationService;

    public WebSocketConfig(UserService userService, NotificationRepository notificationRepository, NotificationService notificationService) {
        this.userService = userService;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationHandler(), "/notification")
                .setAllowedOrigins("*");
        registry.addHandler(tutorialHandler(), "/tutorial")
                .setAllowedOrigins("*");

    }
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxSessionIdleTimeout(-1L); // Set timeout to last indefinitely
        return container;
    }
    @Bean
    WebSocketHandler tutorialHandler() {
        return new TutorialHandler();
    }

    WebSocketHandler notificationHandler() {
        return new NotificationHandler(userService, notificationRepository, notificationService);
    }
}