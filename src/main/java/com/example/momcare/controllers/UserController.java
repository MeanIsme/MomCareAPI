package com.example.momcare.controllers;

import com.example.momcare.models.*;
import com.example.momcare.payload.request.AddUserFollowerRequest;
import com.example.momcare.payload.request.ChangePasswordRequest;
import com.example.momcare.payload.request.CreatePasswordRequest;
import com.example.momcare.payload.request.OPTRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.UserProfile;
import com.example.momcare.payload.response.UserResponse;
import com.example.momcare.security.CheckAccount;
import com.example.momcare.security.Encode;
import com.example.momcare.service.*;
import com.example.momcare.util.Constant;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    private final UserService userService;
    private final SocialPostService socialPostService;
    private final UserStoryService userStoryService;
    private final BabyHealthIndexService babyHealthIndexService;
    private final EmailService emailService;
    private final CheckAccount checkAccount = new CheckAccount();

    public UserController(UserService userService, SocialPostService socialPostService, UserStoryService userStoryService, BabyHealthIndexService babyHealthIndexService, EmailService emailService) {
        this.userService = userService;
        this.socialPostService = socialPostService;
        this.userStoryService = userStoryService;
        this.babyHealthIndexService = babyHealthIndexService;
        this.emailService = emailService;
    }

    Encode encode = new Encode();

    @PostMapping("/signup")
    public Response signUpAccount(@RequestBody User user) {
        switch (checkAccount.checkSignup(user, userService)) {
            case (0):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User name has been used");
            case (3):
                List<MomHealthIndex> momHealthIndices = new ArrayList<>();
                List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
                user.setPremium(false);
                user.setDatePregnant("");
                user.setMomIndex(momHealthIndices);
                user.setBabyIndex(babyHealthIndices);
                user.setNameDisplay(user.getUserName());
                user.setEnabled(false);
                userService.save(user);
                String token = UUID.randomUUID() + "-" + user.getId();
                user.setToken(token);
                userService.update(user);
                try {
                    emailService.sendVerifyEmail(user.getEmail(), token);
                } catch (MessagingException e) {
                    return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), Constant.FAILURE);
                }
                List<UserResponse> users = new ArrayList<>();
                UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
                users.add(userResponse);
                return new Response(HttpStatus.OK.getReasonPhrase(), users, "success");
            case (2):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Password not strength");
            case (1):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Email has been used");
            default:
                break;
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }

    @PostMapping("/login")
    public Response loginAccount(@RequestBody User user) {
        User check = userService.findAccountByUserName(user.getUserName());
        if (check == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);


        if (Objects.equals(user.getUserName(), check.getUserName()) && Objects.equals(encode.encoderPassword(user.getPassWord()), check.getPassWord())){
            if (check.getEnabled().equals(true)) {
                List<UserResponse> users = new ArrayList<>();
                UserResponse userResponse = new UserResponse(check.getId(), check.getUserName(), check.getEmail(), check.getDatePregnant(), check.getPremium(), check.getAvtUrl(), check.getFollower(), check.getFollowing(), check.getNameDisplay());
                users.add(userResponse);
                return new Response(HttpStatus.OK.getReasonPhrase(), users, Constant.SUCCESS);
            }
            if (check.getEnabled().equals(false)) {
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Email not verify");
            }
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }

    @PutMapping("/user/update")
    public Response updateAccount(@RequestBody User user) {
        User check = userService.findAccountByID(user.getId());
        if (check != null) {

            if (user.getDatePregnant() != null) {
                check.setDatePregnant(user.getDatePregnant());
                check.setBabyIndex(babyHealthIndexService.updateDatePregnant(check));
            }
            if (user.getPremium() != null)
                check.setPremium(user.getPremium());
            if (user.getBabyIndex() != null)
                check.setBabyIndex(user.getBabyIndex());
            if (user.getMomIndex() != null)
                check.setMomIndex(user.getMomIndex());
            if (user.getAvtUrl() != null)
                check.setAvtUrl(user.getAvtUrl());
            if (user.getNameDisplay() != null)
                check.setNameDisplay(user.getNameDisplay());
            List<UserResponse> users = new ArrayList<>();
            UserResponse userResponse = new UserResponse(
                    check.getId(),
                    check.getUserName(),
                    check.getEmail(),
                    check.getDatePregnant(),
                    check.getPremium(),
                    check.getAvtUrl(),
                    check.getFollower(),
                    check.getFollowing(),
                    check.getNameDisplay());

            users.add(userResponse);
            userService.update(check);
            return new Response(HttpStatus.OK.getReasonPhrase(), users, Constant.SUCCESS);
        }

        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }

    @GetMapping("user/getById")
    public Response changePassword(@RequestParam String id) {
        User user = userService.findAccountByID(id);
        if (user != null) {
            List<UserResponse> users = new ArrayList<>();
            UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
            users.add(userResponse);
            return new Response((HttpStatus.OK.getReasonPhrase()), users, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @PutMapping("user/changepassword")
    public Response changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = userService.findAccountByID(changePasswordRequest.getId());
        if (user != null) {
            if (Objects.equals(encode.encoderPassword(changePasswordRequest.getOldPassword()), user.getPassWord())) {
                if (checkAccount.checkPassWordstrength(changePasswordRequest.getNewPassword())) {
                    user.setPassWord(changePasswordRequest.getNewPassword());
                    userService.save(user);
                    return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
                } else
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Password not strength");

            } else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Old password not match");

        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @PutMapping("user/forgotpassword")
    public Response forgotPassword(@RequestParam String userName) {
        User user = userService.findAccountByUserName(userName);
        if (user != null) {
            String otp = userService.OTP();
            List<String> opts = new ArrayList<>();
            user.setOtp(otp);
            userService.save(user);
            try {
                emailService.sendOTPEmail(user.getEmail(), otp);
            } catch (MessagingException e) {
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
            }
            opts.add(otp);
            return new Response((HttpStatus.OK.getReasonPhrase()), opts, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @PutMapping("user/optlogin")
    public Response optLogin(@RequestBody OPTRequest optRequest) {
        User user = userService.findAccountByUserName(optRequest.getUserName());
        if (user != null) {
            if (Objects.equals(user.getOtp(), optRequest.getOtp())) {
                String tokenPassword = UUID.randomUUID() + "-" + user.getId();
                user.setPasswordToken(tokenPassword);
                userService.update(user);
                List<String> tokens = new ArrayList<>();
                tokens.add(tokenPassword);
                return new Response((HttpStatus.OK.getReasonPhrase()), tokens, Constant.SUCCESS);
            }
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "OPT not match");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @PutMapping("user/createpassword")
    public Response createpassword(@RequestBody CreatePasswordRequest createPasswordRequest) {
        User user = userService.findAccountByUserName(createPasswordRequest.getUserName());
        if (user != null) {
            if (Objects.equals(user.getPasswordToken(), createPasswordRequest.getToken())) {
                if (checkAccount.checkPassWordstrength(createPasswordRequest.getNewPassword())) {
                    user.setPassWord(createPasswordRequest.getNewPassword());
                    userService.save(user);
                    List<UserResponse> users = new ArrayList<>();
                    UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium());
                    users.add(userResponse);
                    return new Response((HttpStatus.OK.getReasonPhrase()), users, Constant.SUCCESS);
                }
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Password not strength");
            }
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "OPT not match");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @PutMapping("user/follow")
    public Response addFollower(@RequestBody AddUserFollowerRequest userFollower) {
        User userFollowed = userService.findAccountByID(userFollower.getIdFollowingUser());
        User userFollowing = userService.findAccountByID(userFollower.getIdUser());
        if (userFollowed != null && userFollowing != null) {
            Set<String> ids = userFollowed.getFollower();
            if (userFollowed.getFollower() == null)
                ids = new HashSet<>();
            ids.add(userFollower.getIdUser());
            userFollowed.setFollower(ids);
            userService.update(userFollowed);
            Set<String> idsing = userFollowing.getFollowing();
            if (userFollowing.getFollowing() == null)
                idsing = new HashSet<>();
            idsing.add(userFollower.getIdFollowingUser());
            userFollowing.setFollowing(idsing);
            userService.update(userFollowing);
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @PutMapping("user/unfollow")
    public Response unFollow(@RequestBody AddUserFollowerRequest userFollower) {
        User userFollowed = userService.findAccountByID(userFollower.getIdFollowingUser());
        User userFollowing = userService.findAccountByID(userFollower.getIdUser());
        if (userFollowed != null && userFollowing != null) {
            Set<String> ids = userFollowed.getFollower();
            if (userFollowed.getFollower() != null) {
                ids.remove(userFollower.getIdUser());
                userFollowed.setFollower(ids);
                userService.update(userFollowed);
            }
            Set<String> idsing = userFollowing.getFollowing();
            if (userFollowing.getFollowing() != null) {
                idsing.remove(userFollower.getIdFollowingUser());
                userFollowing.setFollowing(idsing);
                userService.update(userFollowing);
            }
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @GetMapping(value = "/verifyemail", produces = MediaType.TEXT_HTML_VALUE)
    public String verifyEmail(@RequestParam String token) {
        User user = userService.findAccountByToken(token);
        if (user != null) {
            user.setEnabled(true);
            userService.update(user);
            return emailService.SUCCESS;
        }
        return emailService.ERROR;
    }

    @PostMapping("/sendmail")
    public void sendMail() {
        try {
            emailService.sendVerifyEmail("Nghiapv74@gmail.com", "chào");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("profile")
    public Response getProfile(@RequestParam String id) {
        User user = userService.findAccountByID(id);
        if (user != null) {
            List<UserProfile> userProfiles = new ArrayList<>();
            UserProfile userProfile = new UserProfile(user.getId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), user.getFollower(), socialPostService.getAllByUser(user.getId()), userStoryService.findByUserId(user.getId()), user.getShared());
            userProfiles.add(userProfile);
            return new Response((HttpStatus.OK.getReasonPhrase()), userProfiles, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }
}