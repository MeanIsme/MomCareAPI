package com.example.momcare.controllers;

import com.example.momcare.models.User;
import com.example.momcare.payload.response.Response;
import com.example.momcare.security.CheckAccount;
import com.example.momcare.security.Encode;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    private CheckAccount checkAccount = new CheckAccount();
    Encode encode = new Encode();

    @PostMapping("/signup")
    public Response signUpAccount(@RequestBody User user){

        switch (checkAccount.checkSignup(user, service)){
            case (0):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User name has been used");
            case (1):
                user.setPremium(false);

                service.save(user);
                List<User> users = new ArrayList<>();
                users.add(user);
                return new Response(HttpStatus.OK.getReasonPhrase(), users , "success");
            case (2):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Password not strength");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @PostMapping("/login")
    public Response loginAccount(@RequestBody User user) {
        User check = service.findAccountByUserName(user.getUserName());
        List<User> users = new ArrayList<>();
        users.add(check);
        if (Objects.equals(user.getUserName(), check.getUserName())){
            if(Objects.equals(encode.encoderPassword(user.getPassWord()), check.getPassWord()))
                return new Response(HttpStatus.OK.getReasonPhrase(), users , "success");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @PutMapping("/user/update")
    public Response updateAccount(@RequestBody User user) {
        User check = service.findAccountByUserName(user.getUserName());
        if(check!=null){
            List<User> users = new ArrayList<>();
            users.add(user);
            service.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), users , "success");
        }


        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @PostMapping("/findbyusername")
    public User findByUsername(@RequestBody User user){
        return service.findAccountByUserName(user.getUserName());
    }
}