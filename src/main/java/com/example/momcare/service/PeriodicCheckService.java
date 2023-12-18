package com.example.momcare.service;

import com.example.momcare.models.PeriodicCheck;
import com.example.momcare.repository.PeriodicCheckRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodicCheckService {
    PeriodicCheckRepository periodicCheckRepository;

    public PeriodicCheckService(PeriodicCheckRepository periodicCheckRepository) {
        this.periodicCheckRepository = periodicCheckRepository;
    }

    public List<PeriodicCheck> findAll(){
        Sort sort = Sort.by(Sort.Direction.ASC, "weekFrom");
        return this.periodicCheckRepository.findAll(sort);
    }
    public PeriodicCheck findByWeekFrom(int weekFrom){
        return this.periodicCheckRepository.findByWeekFrom(weekFrom);
    }
}
