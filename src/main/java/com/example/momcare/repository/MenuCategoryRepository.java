package com.example.momcare.repository;

import com.example.momcare.models.MenuCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuCategoryRepository extends MongoRepository<MenuCategory, String> {
}
