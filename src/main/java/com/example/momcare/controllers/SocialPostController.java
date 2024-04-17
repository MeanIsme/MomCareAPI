package com.example.momcare.controllers;

import com.example.momcare.models.SocialPost;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.ShareResquest;
import com.example.momcare.payload.request.SocialPostNewRequest;
import com.example.momcare.payload.request.SocialPostUpdateResquest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.SocialCommentService;
import com.example.momcare.service.SocialPostService;
import com.example.momcare.service.SocialReactionService;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/socialpost")
public class SocialPostController {

    @Autowired
    SocialPostService socialPostService;
    @Autowired
    SocialCommentService socialCommentService;
    @Autowired
    SocialReactionService socialReactionService;
    @Autowired
    UserService userService;

    @GetMapping("/getallbyuser")
    public Response getAllByUser(@RequestParam String userId) {
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.getAllByUser(userId), "success");
    }

    @GetMapping("/pagebyuser")
    public Response getAllByUser(@RequestParam String userId, int time) {
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.PostPerPageByUser(userId, time), "success");
    }

    @GetMapping("/getall")
    public Response getAll() {
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.getAll(), "success");
    }

    @GetMapping("/like")
    public Response search(@RequestParam String keyWord) {
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.getAll(), "success");
    }

    @GetMapping("/page")
    public Response PostPerPage(@RequestParam int time) {
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.PostPerPage(time), "success");
    }

    @PostMapping("/new")
    public Response create(@RequestBody SocialPostNewRequest request) {
        SocialPost socialPost = new SocialPost(request.getDescription(), request.getUserId(), request.getMedia(), LocalDateTime.now().toString());
        if (socialPostService.save(socialPost)) {
            List<SocialPost> socialPosts = new ArrayList<>();
            socialPosts.add(socialPost);
            return new Response((HttpStatus.OK.getReasonPhrase()), socialPosts, "success");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }

    @PutMapping("/update")
    public Response update(@RequestBody SocialPostUpdateResquest request) {
        SocialPost socialPost = socialPostService.findById(request.getId());
        if (socialPost != null) {
            if (request.getDescription() != null)
                socialPost.setDescription(request.getDescription());
            if (request.getMedia() != null)
                socialPost.setMedia(request.getMedia());
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
    }

    @PutMapping("/share")
    public Response share(@RequestBody ShareResquest request) {
        SocialPost socialPost = socialPostService.findById(request.getIdPost());
        User user = userService.findAccountByID(request.getIdUser());
        if (socialPost != null) {
            if (user != null) {
                Set<String> sharesUser = new HashSet<>(user.getShared());
                sharesUser.add(socialPost.getId());
                user.setShared(sharesUser);
                Set<String> sharesPost = new HashSet<>(socialPost.getShare());
                sharesPost.add(user.getId());
                socialPost.setShare(sharesPost);
                socialPost.setCountShare(socialPost.getCountShare() + 1);
                userService.save(user);
                if (socialPostService.save(socialPost))
                    return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
                else
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Failure");
            }
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User or not found");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post or not found");
    }

    @PutMapping("/unshare")
    public Response unshare(@RequestBody ShareResquest request) {
        SocialPost socialPost = socialPostService.findById(request.getIdPost());
        User user = userService.findAccountByID(request.getIdUser());
        if (socialPost != null) {
            if (user != null) {
                Set<String> sharesUser = new HashSet<>(user.getShared());
                sharesUser.remove(socialPost.getId());
                user.setShared(sharesUser);
                Set<String> sharesPost = new HashSet<>(socialPost.getShare());
                sharesPost.remove(user.getId());
                socialPost.setShare(sharesPost);
                socialPost.setCountShare(socialPost.getCountShare() + 1);
                userService.save(user);
                if (socialPostService.save(socialPost))
                    return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
                else
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Failure");
            }
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User or not found");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post or not found");
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestParam String idPost) {
        SocialPost socialPost = socialPostService.findById(idPost);
        if (socialPost != null) {
            if (socialPostService.delete(socialPost.getId())) {
                Set<String> setComment = socialPost.getComments();
                Set<String> setReaction = socialPost.getReactions();
                if (setComment != null) {
                    for (String id : setComment) {
                        socialCommentService.delete(id);
                    }
                }
                if (setReaction != null) {
                    for (String id : setReaction) {
                        socialReactionService.delete(id);
                    }
                }

                return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
            } else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
    }
}
