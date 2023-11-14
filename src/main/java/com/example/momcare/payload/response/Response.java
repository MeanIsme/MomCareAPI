package com.example.momcare.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Response {
    private String status;
    private List<?> data;
    private String message;

    public Response(String status, List<?> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
