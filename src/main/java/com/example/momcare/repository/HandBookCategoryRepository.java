package com.example.momcare.repository;

import com.example.momcare.models.HandBookCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HandBookCategoryRepository extends MongoRepository<HandBookCategory, String> {
}
