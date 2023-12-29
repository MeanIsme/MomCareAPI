package com.example.momcare.repository;

import com.example.momcare.models.Music;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MusicRepository extends MongoRepository<Music, String> {
    @Query("{'category': ?0}")
    List<Music> findMusicByCategoryIn(String category);
}
