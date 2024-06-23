package com.example.momcare.service;

import com.example.momcare.models.SocialPost;
import com.example.momcare.models.SocialReaction;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.ShareResquest;
import com.example.momcare.payload.request.SocialPostNewRequest;
import com.example.momcare.payload.request.SocialPostUpdateResquest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.SocialPostResponse;
import com.example.momcare.payload.response.SocialReactionResponse;
import com.example.momcare.repository.SocialPostRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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
    @Transactional
    public Response deletePost(String idPost){
        SocialPost socialPost = findById(idPost);
        if (socialPost != null) {
            if (delete(socialPost.getId())) {
                Set<String> setComment = socialPost.getComments();
                if (setComment != null) {
                    for (String id : setComment) {
                        delete(id);
                    }
                }
                return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
            } else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.NOT_FOUND_POST);
    }
    @Transactional
    public Response unshare(ShareResquest request){
        SocialPost socialPost = findById(request.getIdPost());
        User user = userService.findAccountByID(request.getIdUser());
        if (socialPost != null) {
            if (user != null) {
                Set<String> sharesUser = new HashSet<>(user.getShared());
                sharesUser.remove(socialPost.getId());
                user.setShared(sharesUser);
                Set<String> sharesPost = new HashSet<>(socialPost.getShare());
                sharesPost.remove(user.getId());
                socialPost.setShare(sharesPost);
                userService.update(user);
                if (save(socialPost))
                    return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
                else
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
            }
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User or not found");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post or not found");
    }
    
    @Transactional
    public Response share(ShareResquest request){
        SocialPost socialPost = findById(request.getIdPost());
        User user = userService.findAccountByID(request.getIdUser());
        if (socialPost != null) {
            if (user != null) {
                Set<String> sharesUser = new HashSet<>(user.getShared());
                sharesUser.add(socialPost.getId());
                user.setShared(sharesUser);
                Set<String> sharesPost = new HashSet<>(socialPost.getShare());
                sharesPost.add(user.getId());
                socialPost.setShare(sharesPost);
                userService.update(user);
                if (save(socialPost)){
                    return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
                }

                else
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
            }
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User or not found");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post or not found");
    }

    public Response update(SocialPostUpdateResquest request){
        SocialPost socialPost = findById(request.getId());
        if (socialPost != null) {
            User user = userService.findAccountByID(socialPost.getUserId());
            if (user == null)
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
            if (request.getDescription() != null)
                socialPost.setDescription(request.getDescription());
            if (request.getMedia() != null)
                socialPost.setMedia(request.getMedia());
            if (request.getReaction() != null)
                socialPost.setReactions(request.getReaction());
            save(socialPost);
            List<SocialPostResponse> socialPostResponses = new ArrayList<>();
            SocialPostResponse socialPostResponse = new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime());
            socialPostResponses.add(socialPostResponse);
            return new Response((HttpStatus.OK.getReasonPhrase()), socialPostResponses, Constant.SUCCESS);
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.NOT_FOUND_POST);
    }

    public Response create(SocialPostNewRequest request){
        SocialPost socialPost = new SocialPost(request.getDescription(), request.getUserId(), request.getMedia(), LocalDateTime.now().toString());
        if (save(socialPost)) {
            User user = userService.findAccountByID(socialPost.getUserId());
            if (user == null)
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
            List<SocialPostResponse> socialPostResponses = new ArrayList<>();
            SocialPostResponse socialPostResponse = new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime());
            socialPostResponses.add(socialPostResponse);
            return new Response((HttpStatus.OK.getReasonPhrase()), socialPostResponses, Constant.SUCCESS);
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }

    public Response postPerPage(String id){
        SocialPost socialPost = findById(id);
        Map<String, SocialReactionResponse> socialReactionResponseMap = new HashMap<>();
        User user = null;
        SocialReactionResponse socialReactionResponse = null;
        if(socialPost!=null){
            for (String userId: socialPost.getReactions().keySet()) {
                user = userService.findAccountByID(userId);
                if(user==null)
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
                SocialReaction socialReaction = socialPost.getReactions().get(userId);
                socialReactionResponse = new SocialReactionResponse(user.getAvtUrl(),user.getNameDisplay(), socialReaction.getTime(), socialReaction.getReaction());
                socialReactionResponseMap.put(userId, socialReactionResponse);
            }
            List<Map<String, SocialReactionResponse>> list = new ArrayList<>();
            list.add(socialReactionResponseMap);
            return new Response((HttpStatus.OK.getReasonPhrase()), list, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }

    public Response getAllByUserService(String userId){
        User user = userService.findAccountByID(userId);
        if(user==null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
        List<SocialPostResponse> socialPostResponses = new ArrayList<>();
        List<SocialPost> socialPosts = getAllByUser(userId);
        for (SocialPost socialPost : socialPosts) {
            socialPostResponses.add(new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime()));
        }
        return new Response((HttpStatus.OK.getReasonPhrase()), socialPostResponses, Constant.SUCCESS);
    }

    public Response getById(String id){
        return new Response((HttpStatus.OK.getReasonPhrase()), List.of(findByIdResponse(id)), Constant.SUCCESS);
    }

    public Response getAllByUser(String userId, int time){
        return new Response((HttpStatus.OK.getReasonPhrase()), postPerPageByUser(userId, time), Constant.SUCCESS);
    }

    public Response getAllService() {
        return new Response((HttpStatus.OK.getReasonPhrase()), getAll(), Constant.SUCCESS);
    }
    public Response search(String keyWord) {
        return new Response((HttpStatus.OK.getReasonPhrase()), getAll(), Constant.SUCCESS);
    }
    public Response postPerPageService(int time) {
        return new Response((HttpStatus.OK.getReasonPhrase()), postPerPage(time), Constant.SUCCESS);
    }
    public List<SocialPost> postPerPageByUser(String idUser, int time) {
        Pageable pageable = PageRequest.of(time, 20);
        Page<SocialPost> socialPostsPage = this.socialPostRepository.getSocialPostsByUserId(idUser, pageable);
        return socialPostsPage.getContent();
    }
    

    public List<SocialPostResponse> getAll() {
        List<SocialPost> socialPosts = this.socialPostRepository.findAll();
        List<SocialPostResponse> socialPostResponses = new ArrayList<>();
        for (SocialPost socialPost : socialPosts) {
            User user = this.userService.findAccountByID(socialPost.getUserId()) ;
            if(user == null)
                return Collections.emptyList();
            socialPostResponses.add(new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime()));
        }
        return socialPostResponses;
    }


    public List<SocialPost> postPerPage(int time) {
        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        return this.socialPostRepository.findAll(sort).stream()
                .skip(time * 20L).toList();
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
