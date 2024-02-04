package com.example.momcare.repository;

import com.example.momcare.models.HandBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HandBookRepository extends MongoRepository<HandBook,String> {
    List<HandBook> findAllByCategory(String categoryId);

    HandBook getHandBookById(String id);

    @Query("{'title': {$regex : ?0, $options: 'i'}}")
    List<HandBook> findByTitleLike(String keyWord);
}
