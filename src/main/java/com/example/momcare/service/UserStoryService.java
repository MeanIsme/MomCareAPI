package com.example.momcare.service;

import com.example.momcare.models.UserStory;
import com.example.momcare.repository.UserStoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserStoryService {
    UserStoryRepository userStoryRepository;

    public UserStoryService(UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public boolean save(UserStory userStory){
        try {
            this.userStoryRepository.save(userStory);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public UserStory findByUserId(String userId){
        return this.userStoryRepository.findByUserId(userId);
    }

    public boolean delete(String id) {
        try {
            this.userStoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
