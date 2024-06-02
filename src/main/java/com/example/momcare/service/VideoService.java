package com.example.momcare.service;

import com.example.momcare.models.Video;
import com.example.momcare.models.VideoCategory;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.VideoCategoryRepository;
import com.example.momcare.repository.VideoRepository;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VideoService {
    VideoRepository videoRepository;
    VideoCategoryRepository videoCategoryRepository;

    public VideoService(VideoRepository videoRepository, VideoCategoryRepository videoCategoryRepository) {
        this.videoRepository = videoRepository;
        this.videoCategoryRepository = videoCategoryRepository;
    }

    public Response getRandomVideo(){
        return new Response((HttpStatus.OK.getReasonPhrase()), top8Random(), Constant.SUCCESS);
    }
    
    public Response getVideoByCategory(String category){
        return new Response((HttpStatus.OK.getReasonPhrase()), findByCategory(category), Constant.SUCCESS);
    }
    
    public Response getCategory(){
        return new Response((HttpStatus.OK.getReasonPhrase()), getAllCategories(), Constant.SUCCESS);
    }

    public List<Video> top8Random(){
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
