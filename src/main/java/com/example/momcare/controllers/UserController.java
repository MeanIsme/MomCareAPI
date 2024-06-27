package com.example.momcare.controllers;

import com.example.momcare.models.*;
import com.example.momcare.payload.request.*;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.*;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/user/update")
    public Response updateAccount(@RequestBody UserRequest userRequest) {
        User user = userService.convertUserRequestToUser(userRequest);
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), userService.updateAccount(user), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("user/getById")
    public Response changePassword(@RequestParam String id) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), userService.findById(id), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("user/changepassword")
    public Response changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            userService.changePassword(changePasswordRequest);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("user/forgotpassword")
    public Response forgotPassword(@RequestParam String userName) {
        try {
            userService.forgotPassword(userName);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
    @PutMapping("user/optlogin")
    public Response optLogin(@RequestBody OPTRequest optRequest) {
        try {
            userService.optLogin(optRequest.getUserName(), optRequest.getOtp());
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
    @PutMapping("user/createpassword")
    public Response createpassword(@RequestBody CreatePasswordRequest createPasswordRequest) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), userService.createPassword(createPasswordRequest.getUserName(), createPasswordRequest.getToken(), createPasswordRequest.getNewPassword()), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("user/follow")
    public Response addFollower(@RequestBody AddUserFollowerRequest userFollower) {
        try {
            userService.addFollower(userFollower);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
    @PutMapping("user/unfollow")
    public Response unFollow(@RequestBody AddUserFollowerRequest userFollower) {
        try {
            userService.unFollow(userFollower);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping(value = "/verifyemail", produces = MediaType.TEXT_HTML_VALUE)
    public String verifyEmail(@RequestParam String token) {
        return userService.verifyEmail(token);
    }

    @GetMapping("profile")
    public Response getProfile(@RequestParam String id) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), userService.getProfile(id), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("follower")
    public Response getFollower(@RequestParam String id) {
        return new Response(HttpStatus.OK.getReasonPhrase(), userService.getAllFollower(id), Constant.SUCCESS);
    }

    @GetMapping("following")
    public Response getFollowing (@RequestParam String id) {
        return new Response(HttpStatus.OK.getReasonPhrase(), userService.getAllFollowing(id), Constant.SUCCESS);
    }
}