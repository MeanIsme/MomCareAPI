package com.example.momcare.repository;

import com.example.momcare.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account,String> {
    @Query("{userName : ?0}")
    Account findAccountByUserName(String userName);

}