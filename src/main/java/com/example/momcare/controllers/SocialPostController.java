package com.example.momcare.controllers;

import com.example.momcare.models.SocialPost;
import com.example.momcare.payload.request.SocialPostNewRequest;
import com.example.momcare.payload.request.SocialPostUpdateResquest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.SocialCommentService;
import com.example.momcare.service.SocialPostService;
import com.example.momcare.service.SocialReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/socialpost")
public class SocialPostController
{

    @Autowired
    SocialPostService socialPostService;
    @Autowired
    SocialCommentService socialCommentService;
    @Autowired
    SocialReactionService socialReactionService;

    @GetMapping("/getallbyuser")
    public Response getAllByUser(@RequestParam String userId){
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.getAllByUser(userId), "success");
    }

    @GetMapping("/pagebyuser")
    public Response getAllByUser(@RequestParam String userId, int time){
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.PostPerPageByUser(   userId, time), "success");
    }
    @GetMapping("/getall")
    public Response getAll(){
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.getAll(), "success");
    }
    @GetMapping("/page")
    public Response PostPerPage(@RequestParam int time){
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.PostPerPage(time), "success");
    }

    @PostMapping("/new")
    public Response create(@RequestBody SocialPostNewRequest request){
        SocialPost socialPost = new SocialPost(request.getDescription(), request.getUserId(), request.getImages(), LocalDateTime.now().toString());
        if(socialPostService.save(socialPost)) {
            List<SocialPost> socialPosts = new ArrayList<>();
            socialPosts.add(socialPost);
            return new Response((HttpStatus.OK.getReasonPhrase()), socialPosts, "success");
        }

        else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @PutMapping("/update")
    public Response update (@RequestBody SocialPostUpdateResquest request){
        SocialPost socialPost = socialPostService.findById(request.getId());
        if(socialPost!=null)
        {
            if(request.getDescription()!=null)
                socialPost.setDescription(request.getDescription());
            if(request.getImages()!=null)
                socialPost.setImages(request.getImages());
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
        }
        else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
    }
    @DeleteMapping("/delete")
    public Response delete (@RequestParam String idPost){
        SocialPost socialPost = socialPostService.findById(idPost);
        if(socialPost!=null)
        {
            if(socialPostService.delete(socialPost.getId())) {
                Set<String> setComment = socialPost.getComments();
                Set<String> setReaction = socialPost.getReactions();
                if(setComment != null){
                    for (String id : setComment) {
                        socialCommentService.delete(id);
                    }
                }
                if(setReaction != null){
                    for (String id : setReaction) {
                        socialReactionService.delete(id);
                    }
                }

                return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
            }
            else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        }
        else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
    }
}
