package com.example.momcare.repository;

import com.example.momcare.models.HandBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HandBookRepository extends MongoRepository<HandBook,String> {
    @Query("{'category': ?0}")
    List<HandBook> getHandBookByCategory(String id);
}
