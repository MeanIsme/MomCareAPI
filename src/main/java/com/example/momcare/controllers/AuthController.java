package com.example.momcare.controllers;

import com.example.momcare.models.Role;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.UserLoginRequest;
import com.example.momcare.payload.request.UserSignUpRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.UserLoginResponse;
import com.example.momcare.payload.response.UserResponse;
import com.example.momcare.repository.UserRepository;
import com.example.momcare.service.UserService;
import com.example.momcare.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthenticationManagerBuilder authenticationManagerBuilder;
    SecurityUtil securityUtil;
    PasswordEncoder passwordEncoder;
    UserService userService;
    UserRepository userRepository;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, PasswordEncoder passwordEncoder, UserService userService, UserRepository userRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
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
        user.setPassWord(passwordEncoder.encode(userSignUpRequest.getPassword()));
        user.setEmail(userSignUpRequest.getEmail());
        user.setRoles(Role.USER);
        if (userRepository.existsByUserName(user.getUserName())) {
            return new Response(HttpStatus.BAD_REQUEST.getReasonPhrase(), null, "Username is already taken");
        }
        userRepository.save(user);
        UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
        return new Response(HttpStatus.OK.getReasonPhrase(), List.of(userResponse), "success");
    }
}
