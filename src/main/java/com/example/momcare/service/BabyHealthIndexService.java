package com.example.momcare.service;

import com.example.momcare.models.WarningHealth;
import org.springframework.stereotype.Service;

@Service
public class BabyHealthIndexService {
    public WarningHealth CheckHead(int ga, Double head){
        Double mean = -28.2849 + 1.69267 * Math.pow(ga,2) - 0.397485 * Math.pow(ga,2) * Math.log(ga);
        Double sd = 1.98735 + 0.0136772 * Math.pow(ga,3) - 0.00726264 * Math.pow(ga,3) * Math.log(ga) + 0.000976253 * Math.pow(ga,3) *  Math.pow(Math.log(ga),2) ;
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("Head");
        if(mean - sd > head){
            warningHealth.setType("less");
            warningHealth.setLevel(1);
        }
        else if (mean + sd < head) {
            warningHealth.setType("over");
            warningHealth.setLevel(1);
        }
        else {
            warningHealth.setType("normal");
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }
    public WarningHealth CheckBiparietal(int ga, Double biparietal){
        Double mean = 5.60878 +  0.158369 * Math.pow(ga,2) - 0.00256379 * Math.pow(ga,3);
        Double sd = Math.abs(0.101242 + 0.00150557 * Math.pow(ga,3) - 0.000771535 * Math.pow(ga,3) * Math.log(ga) + 0.0000999638 * Math.pow(ga,3) *  Math.pow(Math.log(ga),2));
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("Biparietal");
        if(mean - sd > biparietal){
            warningHealth.setType("less");
            warningHealth.setLevel(1);
        }
        else if (mean + sd < biparietal) {
            warningHealth.setType("over");
            warningHealth.setLevel(1);
        }
        else {
            warningHealth.setType("normal");
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }public WarningHealth CheckOccipitofrontal(int ga, Double occipitofrontal){
        Double mean = -12.4097 + 0.626342 * Math.pow(ga,2) - 0.148075 * Math.pow(ga,2) * Math.log(ga);
        Double sd = Math.abs(-0.880034 + 0.0631165 * Math.pow(ga,2) - 0.0317136 * Math.pow(ga,2) * Math.log(ga) + 0.00408302 * Math.pow(ga,2) *  Math.pow(Math.log(ga),2));
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("Occipitofrontal");
        if(mean - sd > occipitofrontal){
            warningHealth.setType("less");
            warningHealth.setLevel(1);
        }
        else if (mean + sd < occipitofrontal) {
            warningHealth.setType("over");
            warningHealth.setLevel(1);
        }
        else {
            warningHealth.setType("normal");
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }public WarningHealth CheckAbdominal(int ga,  Double abdominal){
        Double mean = -81.3243 + 11.6772 * ga - 0.000561865 * Math.pow(ga,3);
        Double sd = -4.36302 + 0.121445 * Math.pow(ga,2) - 0.0130256 * Math.pow(ga,3) + 0.00282143  * Math.pow(ga,3) * Math.log(ga);
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("Abdominal");
        if(mean - sd > abdominal){
            warningHealth.setType("less");
            warningHealth.setLevel(1);
        }
        else if (mean + sd < abdominal) {
            warningHealth.setType("over");
            warningHealth.setLevel(1);
        }
        else {
            warningHealth.setType("normal");
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }public WarningHealth CheckFemur(int ga, Double femur){
        Double mean = -39.9616 + 4.32298 * ga - 0.0380156 * Math.pow(ga,2);
        Double sd = Math.abs(0.605843 - 42.0014 * Math.pow(ga,-2) + 0.00000917972 * Math.pow(ga,3));
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("Femur");
        if(mean - sd > femur){
            warningHealth.setType("less");
            warningHealth.setLevel(1);
        }
        else if (mean + sd < femur) {
            warningHealth.setType("over");
            warningHealth.setLevel(1);
        }
        else {
            warningHealth.setType("normal");
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }

}
