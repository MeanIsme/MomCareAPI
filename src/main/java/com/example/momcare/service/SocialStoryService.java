package com.example.momcare.service;

import com.example.momcare.models.SocialStory;
import com.example.momcare.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialStoryService {
    StoryRepository storyRepository;

    public SocialStoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }
    public boolean save(SocialStory socialStory) {
        try {
            this.storyRepository.save(socialStory);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean delete(String id) {
        try {
            this.storyRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<SocialStory> getAllByUser(String id){
        return storyRepository.getSocialStoriesByUserId(id);
    }


}
