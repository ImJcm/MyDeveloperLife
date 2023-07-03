package com.example.mydeveloperlife.dto;

import com.example.mydeveloperlife.entity.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private Long category_id;
    private Long user_id;

    public PostResponseDto(Post post) {

    }
}
