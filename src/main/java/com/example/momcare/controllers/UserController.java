package com.example.momcare.controllers;

import com.example.momcare.models.*;
import com.example.momcare.payload.request.*;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Response signUpAccount(@RequestBody UserRequest userRequest) {
        User user = userService.convertUserRequestToUser(userRequest);
        return userService.signUpAccount(user);
    }

    @PostMapping("/login")
    public Response loginAccount(@RequestBody UserRequest userRequest) {
        User user = userService.convertUserRequestToUser(userRequest);
        return userService.loginAccount(user);
    }

    @PutMapping("/user/update")
    public Response updateAccount(@RequestBody UserRequest userRequest) {
        User user = userService.convertUserRequestToUser(userRequest);
        return userService.updateAccount(user);
    }

    @GetMapping("user/getById")
    public Response changePassword(@RequestParam String id) {
        return userService.changePassword(id);
    }

    @PutMapping("user/changepassword")
    public Response changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }

    @PutMapping("user/forgotpassword")
    public Response forgotPassword(@RequestParam String userName) {
        return userService.forgotPassword(userName);
    }
    @PutMapping("user/optlogin")
    public Response optLogin(@RequestBody OPTRequest optRequest) {
        return userService.optLogin(optRequest.getUserName(), optRequest.getOtp());
    }
    @PutMapping("user/createpassword")
    public Response createpassword(@RequestBody CreatePasswordRequest createPasswordRequest) {
        return userService.createPassword(createPasswordRequest.getUserName(), createPasswordRequest.getToken(), createPasswordRequest.getNewPassword());
    }

    @PutMapping("user/follow")
    public Response addFollower(@RequestBody AddUserFollowerRequest userFollower) {
        return userService.addFollower(userFollower);
    }
    @PutMapping("user/unfollow")
    public Response unFollow(@RequestBody AddUserFollowerRequest userFollower) {
        return userService.unFollow(userFollower);
    }

    @GetMapping(value = "/verifyemail", produces = MediaType.TEXT_HTML_VALUE)
    public String verifyEmail(@RequestParam String token) {
        return userService.verifyEmail(token);
    }

    @GetMapping("profile")
    public Response getProfile(@RequestParam String id) {
        return userService.getProfile(id);
    }
}