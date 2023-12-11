package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MomHealthIndexRequest {
    private String userID;
    private int index;
    private Double height;
    private Double weight;
    private Double HATT;
    private Double HATTr;
    private Double GIHungry;
    private Double GIFull1h;
    private Double GIFull2h;
    private Double GIFull3h;
}
