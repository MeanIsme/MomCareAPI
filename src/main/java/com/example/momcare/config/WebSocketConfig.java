package com.example.momcare.config;

import com.example.momcare.handler.NotificationHandler;
import com.example.momcare.handler.TutorialHandler;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService userService;
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
        return new NotificationHandler(userService);
    }
}