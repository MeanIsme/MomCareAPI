package com.example.momcare.repository;

import com.example.momcare.models.HandBookCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HandBookCategoryRepository extends MongoRepository<HandBookCategory, String> {
    @Query("{'collection': ?0}")
    List<HandBookCategory> findByCollectionIn(String collection);

}
