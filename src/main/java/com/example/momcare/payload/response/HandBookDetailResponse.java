package com.example.momcare.payload.response;

import com.example.momcare.models.HandBook;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandBookDetailResponse {
    private String status;
    private HandBook data;
    private String message;

    public HandBookDetailResponse(String status, HandBook data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}

