package com.example.momcare.service;

import com.example.momcare.models.User;
import com.example.momcare.models.UserStory;
import com.example.momcare.payload.request.UserStoryDeleteRequest;
import com.example.momcare.payload.request.UserStoryNewRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.UserStoryResponse;
import com.example.momcare.repository.UserStoryRepository;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserStoryService {
    UserStoryRepository userStoryRepository;
    UserService userService;

    public UserStoryService(UserStoryRepository userStoryRepository, UserService userService) {
        this.userStoryRepository = userStoryRepository;
        this.userService = userService;
    }

    @Transactional
    public Response newUserStory(UserStoryNewRequest request) {
        if (request.getUserId() == null) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
        }
        UserStory userStoryRequest = findByUserId(request.getUserId());

        if (userStoryRequest == null) {
            UserStory userStory = new UserStory(request.getUserId(), request.getSocialStory());
            return getResponse(userStory);
        } else {
            UserStory userStory = new UserStory(userStoryRequest.getId(), request.getUserId(),
                    userStoryRequest.getSocialStories(), request.getSocialStory());
            return getResponse(userStory);
        }


    }

    @Transactional
    public Response deleteStory(UserStoryDeleteRequest request) {
        UserStory userStoryRequest = findByUserId(request.getUserId());
        if (userStoryRequest == null) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
        } else {
            UserStory userStory = new UserStory(userStoryRequest.getId(), userStoryRequest.getUserId(), request.getSocialStories());
            return getResponse(userStory);
        }
    }

    public Response getAllById(String userId) {
        UserStory userStoryRequest = findByUserId(userId);
        if (userStoryRequest == null) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
        } else {
            List<UserStoryResponse> userStoryResponses = new ArrayList<>();
            User user = userService.findAccountByID(userStoryRequest.getUserId());
            userStoryResponses.add(new UserStoryResponse(userStoryRequest.getId(), user.getUserName(), user.getNameDisplay(),
                    userStoryRequest.getUserId(), user.getAvtUrl(), userStoryRequest.getSocialStories()));
            return new Response((HttpStatus.OK.getReasonPhrase()), userStoryResponses, Constant.SUCCESS);
        }
    }

    public Response getAll() {
        List<UserStory> userStories = findall();
        List<UserStoryResponse> userStoryResponses = new ArrayList<>();
        if (userStories == null) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.NOT_FOUND);
        }
        for (UserStory userStory : userStories) {
            User user = userService.findAccountByID(userStory.getUserId());
            if (user != null)
                userStoryResponses.add(new UserStoryResponse(userStory.getId(), user.getUserName(), user.getNameDisplay(),
                        userStory.getUserId(), user.getAvtUrl(), userStory.getSocialStories()));
        }
        return new Response((HttpStatus.OK.getReasonPhrase()), userStoryResponses, Constant.SUCCESS);
    }

    private Response getResponse(UserStory userStory) {
        if (save(userStory)) {
            List<UserStoryResponse> userStoryResponses = new ArrayList<>();
            User user = userService.findAccountByID(userStory.getUserId());
            userStoryResponses.add(new UserStoryResponse(userStory.getId(), user.getUserName(), user.getNameDisplay(),
                    userStory.getUserId(), user.getAvtUrl(), userStory.getSocialStories()));
            return new Response((HttpStatus.OK.getReasonPhrase()), userStoryResponses, Constant.SUCCESS);
        } else {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
        }
    }

    public boolean save(UserStory userStory) {
        try {
            this.userStoryRepository.save(userStory);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserStory findByUserId(String userId) {
        return this.userStoryRepository.findByUserId(userId);
    }

    public boolean delete(String id) {
        try {
            this.userStoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<UserStory> findall() {
        return this.userStoryRepository.findAll();
    }
}
