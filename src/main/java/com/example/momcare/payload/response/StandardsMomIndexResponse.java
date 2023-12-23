package com.example.momcare.payload.response;

import com.example.momcare.models.StandardsIndex;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardsMomIndexResponse {
    private StandardsIndex BMI;
    private StandardsIndex HATT;
    private StandardsIndex HATTr;
    private StandardsIndex GIHungry;
    private StandardsIndex GIFull1h;
    private StandardsIndex GIFull2h;
    private StandardsIndex GIFull3h;
}
