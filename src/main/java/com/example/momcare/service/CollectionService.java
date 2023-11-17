package com.example.momcare.service;

import com.example.momcare.models.Collection;
import com.example.momcare.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    CollectionRepository repository;

    public CollectionService(CollectionRepository repository) {
        this.repository = repository;
    }

    public List<Collection> findAll(){ return this.repository.findAll();}
}
