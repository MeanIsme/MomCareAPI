package com.example.momcare.service;

import com.example.momcare.models.Tracking;
import com.example.momcare.repository.TrackingRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService {
    TrackingRepository trackingRepository;

    public TrackingService (TrackingRepository trackingRepository){
        this.trackingRepository = trackingRepository;
    }

    public Tracking findTrackingByWeek(int week){return this.trackingRepository.findTrackingByWeek(week);}
    public List<Tracking> findAll(){
        Sort sort = Sort.by(Sort.Direction.ASC, "week");
        return trackingRepository.findAll(sort);
    }
}
