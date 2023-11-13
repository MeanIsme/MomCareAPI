package com.example.momcare.service;

import com.example.momcare.models.Account;
import com.example.momcare.repository.AccountRepository;
import com.example.momcare.security.Encode;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    AccountRepository accountRepository;
    Encode encode;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        this.encode = new Encode();
    }

    public void save (Account account){
        String encoderPassword = encode.encoderPassword(account.getPassWord());
        account.setPassWord(encoderPassword);
        this.accountRepository.save(account);
    }

    public List<Account> findAll(){
        return this.accountRepository.findAll();
    }
    public Account findAccountByUserName(String user){
        return this.accountRepository.findAccountByUserName(user);
    }
}
