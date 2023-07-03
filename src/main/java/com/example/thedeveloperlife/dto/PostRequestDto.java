package com.example.thedeveloperlife.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private Long category_id;
}
