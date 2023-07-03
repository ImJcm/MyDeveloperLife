package com.example.mydeveloperlife.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto {

    private int status;
    private String message;

    public ApiResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
