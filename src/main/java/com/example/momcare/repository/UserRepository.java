package com.example.momcare.repository;

import com.example.momcare.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User,String> {
    @Query("{userName : ?0}")
    User findUserByUserName(String userName);

}