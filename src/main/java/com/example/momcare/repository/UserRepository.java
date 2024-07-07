package com.example.momcare.repository;


import com.example.momcare.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface UserRepository extends MongoRepository<User,String> {
    @Query("{userName : ?0}")
    User findUserByUserName(String userName);
    @Query("{_id : ?0}")
    User findUserById(ObjectId id);
    @Query("{token : ?0}")
    User findUserByToken(String token);
    @Query("{email : ?0}")
    User findUserByEmail(String email);
    @Query("{'userName': {$regex : ?0, $options: 'i'}}")
    List<User> findByUserNameLike(String keyWord);

    @Query("{$or: [{'userName': {$regex : ?0, $options: 'i'}}, {'nameDisplay': {$regex : ?0, $options: 'i'}}]}")
    List<User> findByUserNameOrNameDisplayLike(String keyWord);
    boolean existsByUserName(String userName);
}