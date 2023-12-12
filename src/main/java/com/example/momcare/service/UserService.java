package com.example.momcare.service;

import com.example.momcare.models.User;
import com.example.momcare.repository.UserRepository;


import com.example.momcare.security.Encode;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    UserRepository userRepository;
    Encode encode;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.encode = new Encode();
    }

    public void save (User user){
        String encoderPassword = encode.encoderPassword(user.getPassWord());
        user.setPassWord(encoderPassword);
        this.userRepository.save(user);
    }
    public void update (User user){
        this.userRepository.save(user);
    }



    public User findAccountByUserName(String user){
        return this.userRepository.findUserByUserName(user);
    }
    public User findAccountByID(String id){
        ObjectId objectId = new ObjectId(id);
        return this.userRepository.findUserById(objectId);
    }

}