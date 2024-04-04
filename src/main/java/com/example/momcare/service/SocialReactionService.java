package com.example.momcare.service;

import com.example.momcare.models.SocialReaction;
import com.example.momcare.repository.SocialReactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialReactionService {
    SocialReactionRepository socialReactionRepository;

    public SocialReactionService(SocialReactionRepository socialReactionRepository) {
        this.socialReactionRepository = socialReactionRepository;
    }
    public boolean save(SocialReaction socialReaction) {
        try {
            this.socialReactionRepository.save(socialReaction);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean delete(String id){
        try {
            this.socialReactionRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<SocialReaction> findAll(){
        return this.socialReactionRepository.findAll();
    }
    public SocialReaction findById(String id){
        return this.socialReactionRepository.getSocialReactionById(id);
    }
}
