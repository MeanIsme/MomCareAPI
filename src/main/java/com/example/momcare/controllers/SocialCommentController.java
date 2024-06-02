package com.example.momcare.controllers;

import com.example.momcare.payload.request.SocialCommentNewRequest;
import com.example.momcare.payload.request.SocialCommentDeleteRequest;
import com.example.momcare.payload.request.SocialCommentUpdateRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.SocialCommentService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/socialcomment")
public class SocialCommentController {

    SocialCommentService socialCommentService;

    public SocialCommentController(SocialCommentService socialCommentService) {
        this.socialCommentService = socialCommentService;
    }

    @GetMapping("/all")
    public Response getAll() {
        return socialCommentService.getAllSocialComments();
    }

    @GetMapping("/allByPostId")
    public Response getAllByPostId(@RequestParam String id) {
        return socialCommentService.getSocialCommentsById(id);
    }

    @GetMapping("/getAllReactionsByCommentId")
    public Response postPerPage(@RequestParam String id) {
        return socialCommentService.getSocialCommentReactions(id);
    }

    @PostMapping("/new")
    public Response create(@RequestBody SocialCommentNewRequest request) {
        return socialCommentService.create(request);
    }

    @PutMapping("/update")
    public Response update(@RequestBody SocialCommentUpdateRequest request) {
        return socialCommentService.update(request);
    }

    @PutMapping("/addReaction")
    public Response addReaction(@RequestBody SocialCommentUpdateRequest request) {
        return socialCommentService.addReaction(request);
    }

    @PutMapping("/deleteReaction")
    public Response deleteReaction(@RequestBody SocialCommentUpdateRequest request) {
        return socialCommentService.deleteReaction(request);
    }

    @PutMapping("/delete")
    public Response delete(@RequestBody SocialCommentDeleteRequest socialCommentDeleteRequest) {
        return socialCommentService.delete(socialCommentDeleteRequest);

    }
}
