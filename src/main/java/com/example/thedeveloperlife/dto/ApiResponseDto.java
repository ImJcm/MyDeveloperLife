package com.example.thedeveloperlife.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto {

    private int status;
    private String message;
    private Object data;

    public ApiResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
        //this.data = null;
    }

    public ApiResponseDto(int status,String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
