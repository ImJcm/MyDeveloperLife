package com.example.thedeveloperlife.dto;

import com.example.thedeveloperlife.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String userNickname;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.userNickname = comment.getUser().getNickname();
    }

}
