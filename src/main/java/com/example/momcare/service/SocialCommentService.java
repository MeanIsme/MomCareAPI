package com.example.momcare.service;

import com.example.momcare.models.SocialComment;
import com.example.momcare.models.SocialPost;
import com.example.momcare.repository.SocialCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialCommentService {
    SocialCommentRepository socialCommentRepository;

    public SocialCommentService(SocialCommentRepository socialCommentRepository) {
        this.socialCommentRepository = socialCommentRepository;
    }

    public List<SocialComment> findAll(){
        return this.socialCommentRepository.findAll();
    }
    public boolean save(SocialComment socialComment) {
        try {
            this.socialCommentRepository.save(socialComment);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean delete(String id){
        try {
            this.socialCommentRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public SocialComment findById(String id){
        return this.socialCommentRepository.getSocialCommentById(id);
    }
}
