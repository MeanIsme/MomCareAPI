package com.example.momcare.repository;

import com.example.momcare.models.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectionRepository extends MongoRepository<Collection, String> {
}
