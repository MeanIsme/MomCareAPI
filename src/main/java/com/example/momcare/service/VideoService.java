package com.example.momcare.service;

import com.example.momcare.models.Video;
import com.example.momcare.repository.VideoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<Video> findall(){
        Sort sort = Sort.by(Sort.Direction.DESC, "_id");
        return videoRepository.findAll(sort);
    }
    public List<Video> findByCategory(String category){
        return videoRepository.findVideosByCategoryIn(category);
    }
}
