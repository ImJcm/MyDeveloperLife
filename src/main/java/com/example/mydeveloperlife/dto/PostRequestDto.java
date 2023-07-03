package com.example.mydeveloperlife.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private Long category_id;
}
