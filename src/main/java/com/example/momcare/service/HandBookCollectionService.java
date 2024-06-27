package com.example.momcare.service;

import com.example.momcare.models.HandBookCollection;
import com.example.momcare.repository.HandBookCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandBookCollectionService {
    HandBookCollectionRepository repository;

    public HandBookCollectionService(HandBookCollectionRepository repository) {
        this.repository = repository;
    }

    public List<HandBookCollection> findAllCollection() {
        return repository.findAll();

    }
    public List<HandBookCollection> findAll(){ return this.repository.findAll();}
}
