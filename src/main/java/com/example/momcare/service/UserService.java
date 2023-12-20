package com.example.momcare.service;

import com.example.momcare.models.User;
import com.example.momcare.repository.UserRepository;


import com.example.momcare.security.Encode;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

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
    public User findAccountByToken(String token){
        return this.userRepository.findUserByToken(token);
    }
    public int gestationalAge(String datePregnant, String dateEnd){
        LocalDateTime dateStart = LocalDateTime.parse(datePregnant);
        LocalDateTime dateEndTime = LocalDateTime.parse(dateEnd);
        int dateAge = dateEndTime.getDayOfYear() - dateStart.getDayOfYear();
        int weekAge = dateAge/7;
        if(dateAge %2 !=0)
            weekAge += 1;
        return weekAge;
    }

    public String randomPassword(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }

}