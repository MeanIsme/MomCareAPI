package com.example.momcare.service;

import com.example.momcare.models.Music;
import com.example.momcare.repository.MusicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }



    public List<Music> findAll() {
        return this.musicRepository.findAll();
    }
    public List<Music> findMusicByCategory(String category) {
        return this.musicRepository.findMusicByCategoryIn(category);
    }

}
