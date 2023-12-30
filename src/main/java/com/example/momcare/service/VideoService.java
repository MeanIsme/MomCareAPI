package com.example.momcare.service;

import com.example.momcare.models.Category;
import com.example.momcare.models.HandBook;
import com.example.momcare.models.Video;
import com.example.momcare.repository.VideoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoService {
    VideoRepository videoRepository;
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
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

    public List<Video> findByCategory(Category category){
        return videoRepository.findVideosByCategoryIn(category);
    }
    public List<Category> getAllCategories() {
        List<Video> videos = videoRepository.findAll();
        List<Category> uniqueCategories = new ArrayList<>();
        boolean flag;
        for (Video video : videos) {
            for (Category category : video.getCategory()) {
                if(uniqueCategories.isEmpty())
                    uniqueCategories.add(category);
                else {
                    flag = false;
                    List<Category> unCategories = new ArrayList<>();
                    unCategories.addAll(uniqueCategories);
                    for (Category ucategory : unCategories) {
                        if (category.getTitle().equals(ucategory.getTitle())) {
                            flag = true;
                            break;
                        }
                    }
                    if(!flag)
                        uniqueCategories.add(category);
                }
            }
        }
        return uniqueCategories;
    }
}
