package com.example.momcare.repository;

import com.example.momcare.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuRepository extends MongoRepository<Menu, String> {

    List<Menu> findAllByCategory(String category);
    Menu getMenuById(String id);
}
