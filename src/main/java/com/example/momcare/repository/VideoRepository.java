package com.example.momcare.repository;

import com.example.momcare.models.Category;
import com.example.momcare.models.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;

public interface VideoRepository extends MongoRepository<Video, String> {
    @Query("{'category': ?0}")
    List<Video> findVideosByCategoryIn(Category category);

    @Query(value = "{}", fields = "{ 'category' : 1 }")
    List<Video> findAllCategories();
}
