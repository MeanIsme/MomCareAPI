package com.example.momcare.controllers;

import com.example.momcare.models.SocialPost;
import com.example.momcare.models.SocialReaction;
import com.example.momcare.payload.request.SocialReactionDeleteRequest;
import com.example.momcare.payload.request.SocialReactionNewRequest;
import com.example.momcare.payload.request.SocialReactionUpdateRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.SocialPostService;
import com.example.momcare.service.SocialReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/socialreaction")
public class SocialReactionController {
    @Autowired
    SocialReactionService socialReactionService;
    @Autowired
    SocialPostService socialPostService;
    @GetMapping("/all")
    public Response GetAll (){
        List<SocialReaction> socialReactions = socialReactionService.findAll();
        return new Response(HttpStatus.OK.getReasonPhrase(), socialReactions , "success");
    }

    @PostMapping("/new")
    public Response create(@RequestBody SocialReactionNewRequest request){
        SocialReaction socialReaction = new SocialReaction(request.getUserId(), request.getReaction());
        SocialPost socialPost = socialPostService.findById(request.getPostId());
        if(socialPost!=null){
            if(socialReactionService.save(socialReaction)){
                Set<String> setReaction = socialPost.getReactions();
                setReaction.add(socialReaction.getId());
                socialPost.setReactions(setReaction);
                if(socialPostService.save(socialPost))
                    return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
                else {
                    socialReactionService.delete(socialReaction.getId());
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
                }
            }
            else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        }
        else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Not found post");
    }
    @PutMapping("/update")
    public Response update (@RequestBody SocialReactionUpdateRequest request){
        SocialReaction socialReaction = socialReactionService.findById(request.getId());
        if(socialReaction!=null)
        {
            if(request.getReaction()!=null)
                socialReaction.setReaction(request.getReaction());
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
        }
        else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
    }
    @DeleteMapping("/delete")
    public Response delete (@RequestBody SocialReactionDeleteRequest socialReactionDeleteRequest){
        SocialReaction socialReaction = socialReactionService.findById(socialReactionDeleteRequest.getId());
        if(socialReaction!=null)
        {
            if(socialReactionService.delete(socialReaction.getId())){
                SocialPost socialPost =socialPostService.findById(socialReactionDeleteRequest.getPostId());
                if(socialPost!=null) {
                    Set<String> setReaction = socialPost.getReactions();
                    setReaction.remove(socialReaction.getId());
                    socialPost.setReactions(setReaction);
                    if(socialPostService.save(socialPost))
                        return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
                    else{
                        socialReactionService.save(socialReaction);
                        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
                    }
                }
                else
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
            }
            else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        }
        else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Reaction not found");
    }
}
