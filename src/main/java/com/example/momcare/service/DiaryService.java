package com.example.momcare.service;

import com.example.momcare.models.Diary;
import com.example.momcare.repository.DiaryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Diary> Top8Newest(){
        Sort sort = Sort.by(Sort.Direction.DESC, "timeUpdate");
        return diaryRepository.findAll(sort).stream()
                .limit(8)
                .collect(Collectors.toList());
    }

    public List<Diary> DiaryPerPage(int time){
        Sort sort = Sort.by(Sort.Direction.DESC, "timeUpdate");
        return diaryRepository.findAll(sort).stream()
                .skip(time*20L)
                .collect(Collectors.toList());
    }

}
