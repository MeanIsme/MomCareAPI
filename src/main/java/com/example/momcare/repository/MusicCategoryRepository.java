package com.example.momcare.repository;

import com.example.momcare.models.Music;
import com.example.momcare.models.MusicCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicCategoryRepository extends MongoRepository<MusicCategory, String> {
}
