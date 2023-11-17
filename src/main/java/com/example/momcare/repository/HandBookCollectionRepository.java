package com.example.momcare.repository;

import com.example.momcare.models.HandBookCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HandBookCollectionRepository extends MongoRepository<HandBookCollection, String> {
}
