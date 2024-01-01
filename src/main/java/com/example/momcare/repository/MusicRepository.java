package com.example.momcare.repository;

import com.example.momcare.models.Category;
import com.example.momcare.models.Music;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MusicRepository extends MongoRepository<Music, String> {
    @Query("{'category': ?0}")
    List<Music> findMusicByCategory(String category);

    @Query(value = "{}", fields = "{ 'category' : 1 }")
    List<Music> findAllCategories();
}
