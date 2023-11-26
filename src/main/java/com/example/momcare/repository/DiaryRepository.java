package com.example.momcare.repository;

import com.example.momcare.models.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends MongoRepository<Diary, String> {
    @Query("{'idUser': ?0}")
    List<Diary> findAllByIdUser(String idUser);

    Diary getDiaryById(String id);
}
