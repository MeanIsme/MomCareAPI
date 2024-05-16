package com.example.momcare.service;

import com.example.momcare.models.Diary;
import com.example.momcare.models.SocialPost;
import com.example.momcare.models.User;
import com.example.momcare.payload.response.SocialPostResponse;
import com.example.momcare.repository.SocialPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialPostService {

    SocialPostRepository socialPostRepository;
    UserService userService;

    public SocialPostService(SocialPostRepository socialPostRepository, UserService userService) {
        this.socialPostRepository = socialPostRepository;
        this.userService = userService;
    }

    public List<SocialPost> getAllByUser(String idUser) {

        return this.socialPostRepository.getSocialPostsByUserId(idUser);
    }

    public List<SocialPost> PostPerPageByUser(String idUser, int time) {
        Pageable pageable = PageRequest.of(time, 20);
        Page<SocialPost> socialPostsPage = this.socialPostRepository.getSocialPostsByUserId(idUser, pageable);
        return socialPostsPage.getContent();
    }

    public List<SocialPostResponse> getAll() {
        List<SocialPost> socialPosts = this.socialPostRepository.findAll();
        List<SocialPostResponse> socialPostResponses = new ArrayList<>();
        for (SocialPost socialPost : socialPosts) {
            User user = this.userService.findAccountByID(socialPost.getUserId()) ;
            socialPostResponses.add(new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime()));
        }
        return socialPostResponses;
    }


    public List<SocialPost> PostPerPage(int time) {
        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        return this.socialPostRepository.findAll(sort).stream()
                .skip(time * 20L).collect(Collectors.toList());
    }

    public boolean save(SocialPost socialPost) {
        try {
            this.socialPostRepository.save(socialPost);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            this.socialPostRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public SocialPost findById(String id) {
        return this.socialPostRepository.getSocialPostById(id);
    }
    public SocialPostResponse findByIdResponse(String id) {
        User user = this.userService.findAccountByID(this.socialPostRepository.getSocialPostById(id).getUserId());
        SocialPost socialPost = this.socialPostRepository.getSocialPostById(id);
        return new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime());
    }

    public List<SocialPost> searchSocialPostByTitle(String keyWord) {
        return this.socialPostRepository.findByDescriptionLike(keyWord);
    }
}
