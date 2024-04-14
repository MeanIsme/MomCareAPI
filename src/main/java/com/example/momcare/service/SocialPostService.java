package com.example.momcare.service;

import com.example.momcare.models.Diary;
import com.example.momcare.models.SocialPost;
import com.example.momcare.repository.SocialPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialPostService {

    SocialPostRepository socialPostRepository;

    public SocialPostService(SocialPostRepository socialPostRepository) {
        this.socialPostRepository = socialPostRepository;
    }

    public List<SocialPost> getAllByUser(String idUser){

        return this.socialPostRepository.getSocialPostsByUserId(idUser);
    }

    public List<SocialPost> PostPerPageByUser(String idUser, int time){
        Pageable pageable = PageRequest.of(time, 20);
        Page<SocialPost> socialPostsPage = this.socialPostRepository.getSocialPostsByUserId(idUser,pageable);
        return socialPostsPage.getContent();
    }
    public List<SocialPost> getAll(){

        return this.socialPostRepository.findAll();
    }



    public List<SocialPost> PostPerPage(int time){
        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        return this.socialPostRepository.findAll(sort).stream()
                .skip(time*20L).collect(Collectors.toList());
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
