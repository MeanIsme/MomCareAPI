package com.example.momcare.controllers;

import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.models.MomHealthIndex;
import com.example.momcare.models.User;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.UserResponse;
import com.example.momcare.security.CheckAccount;
import com.example.momcare.security.Encode;
import com.example.momcare.service.EmailService;
import com.example.momcare.service.UserService;
import jakarta.mail.MessagingException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    private CheckAccount checkAccount = new CheckAccount();
    Encode encode = new Encode();

    @PostMapping("/signup")
    public Response signUpAccount(@RequestBody User user){
        switch (checkAccount.checkSignup(user, userService)){
            case (0):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User name has been used");
            case (1):
                List<MomHealthIndex> momHealthIndices = new ArrayList<>();
                List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
                user.setPremium(false);
                user.setDatePregnant("");
                user.setMomIndex(momHealthIndices);
                user.setBabyIndex(babyHealthIndices);
                user.setEnabled(false);
                userService.save(user);
                String token = UUID.randomUUID().toString()+ "-" + user.getId();
                user.setToken(token);
                userService.update(user);
                try {
                    emailService.sendEmail(user.getEmail(),"Verify email", token);
                } catch (MessagingException e) {
                    return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>() , "failure");
                }
                List<UserResponse> users = new ArrayList<>();
                UserResponse userResponse = new UserResponse(user.getId(),user.getUserName(),user.getEmail(),user.getDatePregnant(),user.getPremium());
                users.add(userResponse);
                return new Response(HttpStatus.OK.getReasonPhrase(), users , "success");
            case (2):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Password not strength");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @PostMapping("/login")
    public Response loginAccount(@RequestBody User user) {
        User check = userService.findAccountByUserName(user.getUserName());
        if(check == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");


        if (Objects.equals(user.getUserName(), check.getUserName())){
            if(Objects.equals(encode.encoderPassword(user.getPassWord()), check.getPassWord()))
                if(check.getEnabled()){
                    List<UserResponse> users = new ArrayList<>();
                    UserResponse userResponse = new UserResponse(check.getId(),check.getUserName(),check.getEmail(),check.getDatePregnant(),check.getPremium());
                    users.add(userResponse);
                    return new Response(HttpStatus.OK.getReasonPhrase(), users , "success");
                }

                else
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Email not verify");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @PutMapping("/user/update")
    public Response updateAccount(@RequestBody User user) {
        User check = userService.findAccountByID(user.getId());
        if(check!=null){

            if(user.getDatePregnant()!=null)
                check.setDatePregnant(user.getDatePregnant());
            if(user.getPremium()!=null)
                check.setPremium(user.getPremium());
            if(user.getBabyIndex()!=null)
                check.setBabyIndex(user.getBabyIndex());
            if(user.getMomIndex()!=null)
                check.setMomIndex(user.getMomIndex());


            List<UserResponse> users = new ArrayList<>();
            UserResponse userResponse = new UserResponse(check.getId(),check.getUserName(),check.getEmail(),check.getDatePregnant(),check.getPremium());
            users.add(userResponse);
            userService.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), users , "success");
        }


        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @PostMapping("/findbyusername")
    public User findByUsername(@RequestBody User user){
        return userService.findAccountByUserName(user.getUserName());
    }

    @GetMapping(value = "/verifyemail", produces = MediaType.TEXT_HTML_VALUE)
    public String VerifyEmail(@RequestParam String token){
        User user = userService.findAccountByToken(token);
        if(user != null){
            user.setEnabled(true);
            userService.update(user);
            return emailService.success;
        }
        return emailService.error;
    }
    @PostMapping("/sendmail")
    public void SendMail(){
        try {
            emailService.sendEmail("Nghiapv74@gmail.com", "hel123lo", "chào");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}