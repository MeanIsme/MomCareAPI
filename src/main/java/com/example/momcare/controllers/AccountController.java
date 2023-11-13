package com.example.momcare.controllers;

import com.example.momcare.models.Account;
import com.example.momcare.repository.AccountRepository;
import com.example.momcare.security.CheckAccount;
import com.example.momcare.security.Encode;
import com.example.momcare.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class AccountController {
    @Autowired
    private AccountService service;

    private CheckAccount checkAccount = new CheckAccount();
    Encode encode = new Encode();

    @PostMapping("/signup")
    public String signUpAccount(@RequestBody Account account){

        switch (checkAccount.checkSignup(account, service)){
            case (0):
                return "User name has been used";
            case (1):
                account.setPremium(false);

                service.save(account);
                return "sign up success id: " + account.getId();
            case (2):
                return "Password asdasd";
        }

        return "";
    }
    @GetMapping("/hello")
    public String Checking(){
        return "hello";
    }
    @PostMapping("/login")
    public Boolean loginAccount(@RequestBody Account account) {
        Account check = service.findAccountByUserName(account.getUserName());
        if (Objects.equals(account.getUserName(), check.getUserName())){
            if(Objects.equals(encode.encoderPassword(account.getPassWord()), check.getPassWord()))
                return true;
        }
        return false;
    }
    @PostMapping("/findall")
    public List<Account> findAllAccount(){
        return service.findAll();
    }
    @PostMapping("/findbyusername")
    public Account findByUsername(@RequestBody Account account){
        return service.findAccountByUserName(account.getUserName());
    }
}