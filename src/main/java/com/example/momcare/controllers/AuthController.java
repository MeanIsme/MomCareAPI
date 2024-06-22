package com.example.momcare.controllers;

import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.models.MomHealthIndex;
import com.example.momcare.models.Role;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.UserLoginRequest;
import com.example.momcare.payload.request.UserSignUpRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.UserLoginResponse;
import com.example.momcare.payload.response.UserResponse;
import com.example.momcare.repository.UserRepository;
import com.example.momcare.security.CheckAccount;
import com.example.momcare.service.EmailService;
import com.example.momcare.service.UserService;
import com.example.momcare.util.Constant;
import com.example.momcare.util.SecurityUtil;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthenticationManagerBuilder authenticationManagerBuilder;
    SecurityUtil securityUtil;
    PasswordEncoder passwordEncoder;
    UserService userService;
    UserRepository userRepository;
    CheckAccount checkAccount;
    EmailService emailService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, PasswordEncoder passwordEncoder, UserService userService, UserRepository userRepository, CheckAccount checkAccount, EmailService emailService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
        this.checkAccount = checkAccount;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody UserLoginRequest userLoginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = securityUtil.createAccessToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserLoginResponse response = new UserLoginResponse();
        User currentUser = userService.findAccountByUserName(userLoginRequest.getUsername());

        if(currentUser != null) {

            if (currentUser.getEnabled() == null || !currentUser.getEnabled()) {
                return ResponseEntity.ok()
                        .body(new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), Constant.EMAIL_NOT_VERIFY));
            }
            response.setId(currentUser.getId());
            response.setRole(currentUser.getRoles().toString());
        }

        response.setToken(accessToken);

        Response res = new Response(HttpStatus.OK.getReasonPhrase(), List.of(response), "success");

        return ResponseEntity.ok()
                .body(res);
    }
    @PostMapping("/signup")
    public Response signUpAccount(@RequestBody UserSignUpRequest userSignUpRequest) {

        User user = new User();
        user.setUserName(userSignUpRequest.getUserName());
        if (userRepository.existsByUserName(user.getUserName())) {
            return new Response(HttpStatus.BAD_REQUEST.getReasonPhrase(), null, "Username is already taken");
        }

        user.setPassWord(passwordEncoder.encode(userSignUpRequest.getPassword()));
        user.setEmail(userSignUpRequest.getEmail());
        user.setRoles(Role.USER);
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
                UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
                return new Response(HttpStatus.OK.getReasonPhrase(), List.of(userResponse), Constant.SUCCESS);
            case (2):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.PASSWORD_NOT_STRENGTH);
            case (1):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.EMAIL_HAS_BEEN_USED);
            default:
                break;
        }

        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }
}
