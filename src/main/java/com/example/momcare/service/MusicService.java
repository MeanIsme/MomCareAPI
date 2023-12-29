package com.example.momcare.service;

import com.example.momcare.models.Category;
import com.example.momcare.models.Music;
import com.example.momcare.models.Video;
import com.example.momcare.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MusicService {
    MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }



    public List<Music> Top8Random() {
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
    public List<Music> findMusicByCategory(Category category) {
        return this.musicRepository.findMusicByCategoryIn(category);
    }

    public List<Category> getAllCategories() {
        List<Music> musics = musicRepository.findAll();
        List<Category> uniqueCategories = new ArrayList<>();
        boolean flag;
        for (Music music : musics) {
            for (Category category : music.getCategory()) {
                if(uniqueCategories.isEmpty())
                    uniqueCategories.add(category);
                else {
                    flag = false;
                    List<Category> unCategories = new ArrayList<>();
                    unCategories.addAll(uniqueCategories);
                    for (Category ucategory : unCategories) {
                        if (category.getTitle().equals(ucategory.getTitle())) {
                            flag = true;
                        }
                    }
                    if(flag!=true)
                        uniqueCategories.add(category);
                }
            }
        }
        return uniqueCategories;
    }
}
