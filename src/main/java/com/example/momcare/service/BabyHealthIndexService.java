package com.example.momcare.service;

import org.springframework.stereotype.Service;

@Service
public class BabyHealthIndexService {
    public String CheckHead(int ga){
        Double mean = -28.2849 + 1.69267 * Math.pow(ga,2) - 0.397485 * Math.pow(ga,2) * Math.log(ga);
        Double sd = 1.98735 + 0.0136772 * Math.pow(ga,3) - 0.00726264 * Math.pow(ga,3) * Math.log(ga) + 0.000976253 * Math.pow(ga,3) *  Math.pow(Math.log(ga),2) ;
        return "";
    }
    public String CheckBiparietal(int ga){
        Double mean = 5.60878 +  0.158369 * Math.pow(ga,2) - 0.00256379 * Math.pow(ga,3);
        Double sd = Math.abs(0.101242 + 0.00150557 * Math.pow(ga,3) - 0.000771535 * Math.pow(ga,3) * Math.log(ga) + 0.0000999638 * Math.pow(ga,3) *  Math.pow(Math.log(ga),2));
        return "";
    }public String CheckOccipitofrontal(int ga){
        Double mean = -12.4097 + 0.626342 * Math.pow(ga,2) - 0.148075 * Math.pow(ga,2) * Math.log(ga);
        Double sd = Math.abs(-0.880034 + 0.0631165 * Math.pow(ga,2) - 0.0317136 * Math.pow(ga,2) * Math.log(ga) + 0.00408302 * Math.pow(ga,2) *  Math.pow(Math.log(ga),2));
        return "";
    }public String CheckAbdominal(int ga){
        Double mean = -81.3243 + 11.6772 * ga - 0.000561865 * Math.pow(ga,3);
        Double sd = -4.36302 + 0.121445 * Math.pow(ga,2) - 0.0130256 * Math.pow(ga,3) + 0.00282143  * Math.pow(ga,3) * Math.log(ga);
        return "";
    }public String CheckFemur(int ga){
        Double mean = -39.9616 + 4.32298 * ga - 0.0380156 * Math.pow(ga,2);
        Double sd = Math.abs(0.605843 - 42.0014 * Math.pow(ga,-2) + 0.00000917972 * Math.pow(ga,3));
        return "";
    }

}
