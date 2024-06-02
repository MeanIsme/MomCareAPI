package com.example.momcare.service;

import com.example.momcare.models.Diary;
import com.example.momcare.payload.request.DiaryRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.DiaryRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public Response createDiary(DiaryRequest diaryRequest) {
        Diary diary = new Diary(
                diaryRequest.getIdUser(),
                diaryRequest.getTitle(),
                diaryRequest.getContent(),
                diaryRequest.getThumbnail(),
                diaryRequest.getReaction(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString());

        if (save(diary)) {
            return new Response(HttpStatus.OK.getReasonPhrase(), List.of(diary), Constant.SUCCESS);
        } else {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), Constant.FAILURE);
        }
    }

    public Response findAllDiaryByUser(String idUser) {
        List<Diary> diaries = findDiaryByIdUser(idUser);
        return new Response(HttpStatus.OK.getReasonPhrase(), diaries, Constant.SUCCESS);
    }

    public Response findDiaryByIdService(String id) {
        List<Diary> diaries = new ArrayList<>();
        Diary diary = findDiaryById(id);
        if (diary != null) {
            diaries.add(diary);
            return new Response(HttpStatus.OK.getReasonPhrase(), diaries, Constant.SUCCESS);
        } else {
            return new Response(HttpStatus.NOT_FOUND.getReasonPhrase(), new ArrayList<>(), Constant.DIARY_NOT_FOUND);
        }
    }

    @Transactional
    public Response updateDiary(DiaryRequest diaryRequest) {
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
                List<Diary> diaries = new ArrayList<>();
                diaries.add(diary);
                return new Response(HttpStatus.OK.getReasonPhrase(), diaries, Constant.SUCCESS);
            } else {
                return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), Constant.FAILURE);
            }
        } else {
            return new Response(HttpStatus.NOT_FOUND.getReasonPhrase(), new ArrayList<>(), Constant.DIARY_NOT_FOUND);
        }
    }

    public Response getTop8NewestDiaries() {
        List<Diary> top8NewestDiaries = top8Newest();
        return new Response(HttpStatus.OK.getReasonPhrase(), top8NewestDiaries, Constant.SUCCESS);
    }

    @Transactional
    public Response deleteDiary(String id) {
        Diary diary = findDiaryById(id);
        if (diary != null) {
            if (delete(diary)) {
                return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
            } else {
                return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), Constant.FAILURE);
            }
        } else {
            return new Response(HttpStatus.NOT_FOUND.getReasonPhrase(), new ArrayList<>(), Constant.DIARY_NOT_FOUND);
        }
    }

    public Response getDiaryPerPage(int time) {
        List<Diary> diariesPerPage = diaryPerPage(time);
        return new Response(HttpStatus.OK.getReasonPhrase(), diariesPerPage, Constant.SUCCESS);
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
