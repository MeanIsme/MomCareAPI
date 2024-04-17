package com.example.momcare.controllers;

import com.example.momcare.models.SocialComment;
import com.example.momcare.models.SocialPost;
import com.example.momcare.payload.request.SocialCommentNewRequest;
import com.example.momcare.payload.request.SocialCommentDeleteRequest;
import com.example.momcare.payload.request.SocialPostUpdateResquest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.SocialCommentService;
import com.example.momcare.service.SocialPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/socialcomment")
public class SocialCommentController {
    @Autowired
    SocialCommentService socialCommentService;
    @Autowired
    SocialPostService socialPostService;

    @GetMapping("/all")
    public Response GetAll() {
        List<SocialComment> socialComments = socialCommentService.findAll();
        return new Response(HttpStatus.OK.getReasonPhrase(), socialComments, "success");
    }

    @GetMapping("/allByPostId")
    public Response GetAllByPostId(@RequestParam String id) {
        List<SocialComment> socialComments = socialCommentService.findAllById(id);
        return new Response(HttpStatus.OK.getReasonPhrase(), socialComments, "success");
    }

    @PostMapping("/new")
    public Response create(@RequestBody SocialCommentNewRequest request) {
        SocialComment socialComment = new SocialComment(request.getUserId(), request.getPostId(), request.getCommentId(),
                request.getDescription(), LocalDateTime.now().toString());
        SocialPost socialPost = socialPostService.findById(request.getPostId());
        if (socialPost != null) {
            SocialComment comment = socialCommentService.save(socialComment);
            if (comment != null) {
                Set<String> setComment = socialPost.getComments();
                if (setComment == null) {
                    setComment = new HashSet<>();
                }
                setComment.add(socialComment.getId());
                socialPost.setComments(setComment);
                socialPost.setCountComments(socialPost.getComments().size());
                if (socialPostService.save(socialPost)) {
                    List<SocialComment> socialComments = new ArrayList<>();
                    socialComments.add(comment);
                    return new Response((HttpStatus.OK.getReasonPhrase()), socialComments, "success");
                } else {
                    socialCommentService.delete(socialComment.getId());
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
                }
            } else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Not found post");

    }

    @PutMapping("/update")
    public Response update(@RequestBody SocialPostUpdateResquest request) {
        SocialComment socialComment = socialCommentService.findById(request.getId());
        if (socialComment != null) {
            if (request.getDescription() != null)
                socialComment.setDescription(request.getDescription());
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
    }

    @PutMapping("/delete")
    public Response delete(@RequestBody SocialCommentDeleteRequest socialCommentDeleteRequest) {
        SocialComment socialComment = socialCommentService.findById(socialCommentDeleteRequest.getId());
        if (socialComment != null) {
            if (socialCommentService.delete(socialComment.getId())) {
                SocialPost socialPost = socialPostService.findById(socialCommentDeleteRequest.getPostId());
                if (socialPost != null) {
                    Set<String> setComment = socialPost.getComments();
                    setComment.remove(socialComment.getId());
                    socialPost.setComments(setComment);
                    socialPost.setCountComments(socialPost.getComments().size());
                    if (socialPostService.save(socialPost)) {
                        return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
                    } else {
                        socialCommentService.save(socialComment);
                        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
                    }
                } else {
                    socialCommentService.save(socialComment);
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
                }

            } else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
    }
}
