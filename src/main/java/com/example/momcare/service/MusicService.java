package com.example.momcare.service;

import com.example.momcare.models.Music;
import com.example.momcare.models.MusicCategory;
import com.example.momcare.repository.MusicCategoryRepository;
import com.example.momcare.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MusicService {
    MusicRepository musicRepository;
    MusicCategoryRepository musicCategoryRepository;

    public MusicService(MusicRepository musicRepository, MusicCategoryRepository musicCategoryRepository) {
        this.musicRepository = musicRepository;
        this.musicCategoryRepository = musicCategoryRepository;
    }
    public List<Music> getRandomMusic(){
        return top8Random();
    }
    public List<Music> getMusicByCategory(String category){
        return findMusicByCategory(category);
    }
    public List<MusicCategory> getCategory(){
        return getAllCategories();
    }

    public List<Music> top8Random() {
        List<Music> allMusic= musicRepository.findAll();
        int totalMusic = allMusic.size();
        if (totalMusic <= 8) {
            return allMusic;
        }
        Random random = new Random();
        int[] indexes = random.ints(0, totalMusic).distinct().limit(8).toArray();

        List<Music> randomDocuments = new ArrayList<>();
        for (int index : indexes) {
            randomDocuments.add(allMusic.get(index));
        }

        return randomDocuments;
    }
    public List<Music> findMusicByCategory(String category) {
        return this.musicRepository.findMusicByCategory(category);
    }

    public List<MusicCategory> getAllCategories() {
        return this.musicCategoryRepository.findAll();
    }
}
