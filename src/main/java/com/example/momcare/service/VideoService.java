package com.example.momcare.service;

import com.example.momcare.models.Category;
import com.example.momcare.models.HandBook;
import com.example.momcare.models.Video;
import com.example.momcare.models.VideoCategory;
import com.example.momcare.repository.MusicCategoryRepository;
import com.example.momcare.repository.VideoCategoryRepository;
import com.example.momcare.repository.VideoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoService {
    VideoRepository videoRepository;
    VideoCategoryRepository videoCategoryRepository;

    public VideoService(VideoRepository videoRepository, VideoCategoryRepository videoCategoryRepository) {
        this.videoRepository = videoRepository;
        this.videoCategoryRepository = videoCategoryRepository;
    }

    public List<Video> Top8Random(){
        List<Video> allVideo = videoRepository.findAll();
        int totalVideo = allVideo.size();
        if (totalVideo <= 8) {
            return allVideo;
        }
        Random random = new Random();
        int[] indexes = random.ints(0, totalVideo).distinct().limit(8).toArray();
        List<Video> randomDocuments = new ArrayList<>();
        for (int index : indexes) {
            randomDocuments.add(allVideo.get(index));
        }
        return randomDocuments;
    }

    public List<Video> findByCategory(String category){
        return videoRepository.findVideosByCategory(category);
    }
    public List<VideoCategory> getAllCategories() {
        return this.videoCategoryRepository.findAll();
    }
}
