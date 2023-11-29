package com.example.momcare.service;

import com.example.momcare.models.Diary;
import com.example.momcare.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {
    DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }


    public List<Diary> findDiaryByIdUser(String idUser){
        return this.diaryRepository.findAllByIdUser(idUser);
    }
    public Diary findDiaryById(String id){
        return  this.diaryRepository.getDiaryById(id);
    }
    public boolean save(Diary diary) {
        try {
            this.diaryRepository.save(diary);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean delete(Diary diary){
        try {
            this.diaryRepository.deleteById(diary.getId());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
