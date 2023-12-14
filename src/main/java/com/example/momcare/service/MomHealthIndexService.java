package com.example.momcare.service;

import com.example.momcare.models.WarningHealth;
import org.springframework.stereotype.Service;

@Service
public class MomHealthIndexService {
    private WarningHealth CheckBMI(Double height, Double weight){
        WarningHealth warningHealth = new WarningHealth();
        return warningHealth;
    }
}
