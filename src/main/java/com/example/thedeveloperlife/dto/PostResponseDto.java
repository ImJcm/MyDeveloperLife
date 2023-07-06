package com.example.thedeveloperlife.dto;

import com.example.thedeveloperlife.entity.Comment;
import com.example.thedeveloperlife.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long category_id;
    private Long user_id;
    private String userName;
    private String userNickName;
    private List<CommentResponseDto> commentResponseDtoList;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.category_id = post.getCategory().getId();
        this.user_id = post.getUser().getId();
        this.userName = post.getUser().getName();
        this.userNickName = post.getUser().getNickname();
        if(post.getCommentList().size()>0) {
            this.commentResponseDtoList = new ArrayList<>();
            for (Comment comment : post.getCommentList()) {
                this.commentResponseDtoList.add(new CommentResponseDto(comment));
            }
        }// end of the if()
    }// end of constructor method()
}
