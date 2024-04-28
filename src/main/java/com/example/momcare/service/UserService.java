package com.example.momcare.service;

import com.example.momcare.models.User;
import com.example.momcare.payload.response.UserResponse;
import com.example.momcare.repository.UserRepository;


import com.example.momcare.security.Encode;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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

    public List<UserResponse> searchUserByUserName(String keyWord){
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findByUserNameLike(keyWord);
        for (User user : users)
            userResponses.add(new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium()));
        return userResponses;
    }


    public User findAccountByUserName(String user){
        return this.userRepository.findUserByUserName(user);
    }

    public User findAccountByEmail(String email){ return this.userRepository.findUserByEmail(email);}

    public User findAccountByID(String id){
        ObjectId objectId = new ObjectId(id);
        return this.userRepository.findUserById(objectId);
    }
    public User findAccountByToken(String token){
        return this.userRepository.findUserByToken(token);
    }
    public int gestationalAge(String datePregnant, String dateEnd){
        if(datePregnant.isEmpty())
            return 0;
        LocalDateTime dateStart = LocalDateTime.parse(datePregnant);
        LocalDateTime dateEndTime = LocalDateTime.parse(dateEnd);
        return (int) ChronoUnit.WEEKS.between(dateStart,dateEndTime);
    }

    public String OTP(){
        // Using numeric values
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);

    }

}