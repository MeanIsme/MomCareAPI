package com.example.momcare.service;

import com.example.momcare.models.Diary;
import com.example.momcare.models.SocialPost;
import com.example.momcare.repository.SocialPostRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialPostService {

    SocialPostRepository socialPostRepository;

    public SocialPostService(SocialPostRepository socialPostRepository) {
        this.socialPostRepository = socialPostRepository;
    }

    public List<SocialPost> getAllByUser(String idUser){

        return this.socialPostRepository.getSocialPostsByUserId(idUser);
    }
    public List<SocialPost> getAll(){

        return this.socialPostRepository.findAll();
    }

    public boolean save(SocialPost socialPost) {
        try {
            this.socialPostRepository.save(socialPost);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean delete(String id){
        try {
            this.socialPostRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public SocialPost findById(String id){
        return this.socialPostRepository.getSocialPostById(id);
    }
}
