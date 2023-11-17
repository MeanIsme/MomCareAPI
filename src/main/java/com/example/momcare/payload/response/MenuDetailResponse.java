package com.example.momcare.payload.response;


import com.example.momcare.models.Menu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDetailResponse {
    private String status;
    private Menu data;
    private String message;

    public MenuDetailResponse(String status, Menu data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}

