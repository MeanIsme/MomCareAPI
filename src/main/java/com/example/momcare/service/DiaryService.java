package com.example.momcare.service;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.Diary;
import com.example.momcare.payload.request.DiaryRequest;
import com.example.momcare.repository.DiaryRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiaryService {
    DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public Diary createDiary(DiaryRequest diaryRequest) throws ResourceNotFoundException {
        Diary diary = new Diary(
                diaryRequest.getIdUser(),
                diaryRequest.getTitle(),
                diaryRequest.getContent(),
                diaryRequest.getThumbnail(),
                diaryRequest.getReaction(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString());

        if (save(diary)) {
            return diary;
        } else {
            throw new ResourceNotFoundException(Constant.FAILURE);
        }
    }

    public List<Diary> findAllDiaryByUser(String idUser) {
        return findDiaryByIdUser(idUser);
    }

    public List<Diary> findDiaryByIdService(String id) throws ResourceNotFoundException {
        List<Diary> diaries = new ArrayList<>();
        Diary diary = findDiaryById(id);
        if (diary != null) {
            diaries.add(diary);
            return diaries;
        } else {
            throw new ResourceNotFoundException(Constant.DIARY_NOT_FOUND);
        }
    }

    @Transactional
    public List<Diary> updateDiary(DiaryRequest diaryRequest) throws ResourceNotFoundException {
        Diary diary = new Diary(
                diaryRequest.getId(),
                diaryRequest.getIdUser(),
                diaryRequest.getTitle(),
                diaryRequest.getContent(),
                diaryRequest.getThumbnail(),
                diaryRequest.getReaction(),
                diaryRequest.getTimeCreate(),
                LocalDateTime.now().toString());
        Diary diaryCheck = findDiaryById(diaryRequest.getId());
        if (diaryCheck != null) {
            diaryRequest.setTimeUpdate(LocalDateTime.now().toString());
            if (save(diary)) {
                return List.of(diary);
            } else {
                throw new ResourceNotFoundException(Constant.FAILURE);
            }
        } else {
            throw new ResourceNotFoundException(Constant.NOT_FOUND);
        }
    }

    public List<Diary> getTop8NewestDiaries() {
        return top8Newest();
    }

    @Transactional
    public void deleteDiary(String id) throws ResourceNotFoundException {
        Diary diary = findDiaryById(id);
        if (diary != null) {
            if (!delete(diary)) {
                throw new ResourceNotFoundException(Constant.FAILURE);
            }
        } else {
            throw new ResourceNotFoundException(Constant.DIARY_NOT_FOUND);
        }
    }

    public List<Diary> getDiaryPerPage(int time) {
        return diaryPerPage(time);
    }

    public List<Diary> findDiaryByIdUser(String idUser) {
        return this.diaryRepository.findAllByIdUser(idUser);
    }

    public Diary findDiaryById(String id) {
        return this.diaryRepository.getDiaryById(id);
    }

    public boolean save(Diary diary) {
        try {
            this.diaryRepository.save(diary);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Diary diary) {
        try {
            this.diaryRepository.deleteById(diary.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Diary> top8Newest() {
        Sort sort = Sort.by(Sort.Direction.DESC, Constant.TIME_UPDATE);
        return diaryRepository.findAll(sort).stream()
                .limit(8)
                .toList();
    }

    public List<Diary> diaryPerPage(int time) {
        Sort sort = Sort.by(Sort.Direction.DESC, Constant.TIME_UPDATE);
        return diaryRepository.findAll(sort).stream()
                .skip(time * 20L)
                .toList();
    }

}
