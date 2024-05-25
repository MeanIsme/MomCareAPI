package com.example.momcare.controllers;

import com.example.momcare.handler.NotificationHandler;
import com.example.momcare.models.SocialPost;
import com.example.momcare.models.SocialReaction;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.ShareResquest;
import com.example.momcare.payload.request.SocialPostNewRequest;
import com.example.momcare.payload.request.SocialPostUpdateResquest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.SocialPostResponse;
import com.example.momcare.payload.response.SocialReactionResponse;
import com.example.momcare.service.SocialCommentService;
import com.example.momcare.service.SocialPostService;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/socialpost")
public class SocialPostController {

    @Autowired
    SocialPostService socialPostService;
    @Autowired
    SocialCommentService socialCommentService;
    @Autowired
    UserService userService;
    @Autowired
    NotificationHandler notificationHandler;

    @GetMapping("/getallbyuser")
    public Response getAllByUser(@RequestParam String userId) {
        User user = userService.findAccountByID(userId);
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<SocialPostResponse> socialPostResponses = new ArrayList<>();
        List<SocialPost> socialPosts = socialPostService.getAllByUser(userId);
        for (SocialPost socialPost : socialPosts) {
            SocialPostResponse socialPostResponse = new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime());
            socialPostResponses.add(socialPostResponse);
        }
        return new Response((HttpStatus.OK.getReasonPhrase()),socialPostResponses, "success");
    }

    @GetMapping("/getById")
    public Response getById(@RequestParam String id) {
        return new Response((HttpStatus.OK.getReasonPhrase()), (List<?>) socialPostService.findByIdResponse(id), "success");
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
    @GetMapping("/getAllReactionsByPostId")
    public Response PostPerPage(@RequestParam String id) {
        SocialPost socialPost = socialPostService.findById(id);
        Map<String, SocialReactionResponse> socialReactionResponseMap = new HashMap<>();
        User user = null;
        SocialReactionResponse socialReactionResponse = null;
        if(socialPost!=null){
            for (String userId: socialPost.getReactions().keySet()) {
                user = userService.findAccountByID(userId);
                if(user==null)
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
                SocialReaction socialReaction = socialPost.getReactions().get(userId);
                socialReactionResponse = new SocialReactionResponse(user.getAvtUrl(),user.getNameDisplay(), socialReaction.getTime(), socialReaction.getReaction());
                socialReactionResponseMap.put(userId, socialReactionResponse);
            }
            List<Map<String, SocialReactionResponse>> list = new ArrayList<>();
            list.add(socialReactionResponseMap);
            return new Response((HttpStatus.OK.getReasonPhrase()), list, "success");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @PostMapping("/new")
    public Response create(@RequestBody SocialPostNewRequest request) {
        SocialPost socialPost = new SocialPost(request.getDescription(), request.getUserId(), request.getMedia(), LocalDateTime.now().toString());
        if (socialPostService.save(socialPost)) {
            User user = userService.findAccountByID(socialPost.getUserId());
            if (user == null)
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
            List<SocialPostResponse> socialPostResponses = new ArrayList<>();
            SocialPostResponse socialPostResponse = new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime());
            socialPostResponses.add(socialPostResponse);
            return new Response((HttpStatus.OK.getReasonPhrase()), socialPostResponses, "success");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }

    @PutMapping("/update")
    public Response update(@RequestBody SocialPostUpdateResquest request) {
        SocialPost socialPost = socialPostService.findById(request.getId());
        if (socialPost != null) {
            User user = userService.findAccountByID(socialPost.getUserId());
            if (user == null)
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
            if (request.getDescription() != null)
                socialPost.setDescription(request.getDescription());
            if (request.getMedia() != null)
                socialPost.setMedia(request.getMedia());
            if (request.getReaction() != null)
                socialPost.setReactions(request.getReaction());
            socialPostService.save(socialPost);
            List<SocialPostResponse> socialPostResponses = new ArrayList<>();
            SocialPostResponse socialPostResponse = new SocialPostResponse(socialPost.getId(), socialPost.getDescription(), socialPost.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialPost.getReactions(), socialPost.getComments(), socialPost.getShare(), socialPost.getMedia(), socialPost.getTime());
            socialPostResponses.add(socialPostResponse);
            return new Response((HttpStatus.OK.getReasonPhrase()), socialPostResponses, "success");
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
                userService.save(user);
                if (socialPostService.save(socialPost)){
                    return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
                }

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
                if (setComment != null) {
                    for (String id : setComment) {
                        socialCommentService.delete(id);
                    }
                }

                return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
            } else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Post not found");
    }
}
