package com.example.thedeveloperlife.entity;

import com.example.thedeveloperlife.dto.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id",nullable = false)
    private Category category;

    @OneToMany( mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public Post(PostRequestDto requestDto, User user, Category category) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
        this.category = category;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    public void update(PostRequestDto postRequestDto, Category category) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = category;
    }
}
