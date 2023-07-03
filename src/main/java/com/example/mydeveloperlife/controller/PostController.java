package com.example.mydeveloperlife.controller;

import com.example.mydeveloperlife.dto.PostRequestDto;
import com.example.mydeveloperlife.dto.PostResponseDto;
import com.example.mydeveloperlife.security.UserDetailsImpl;
import com.example.mydeveloperlife.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(requestDto, userDetails.getUser());
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/post/{id}")
    public PostResponseDto lookupPost(@PathVariable Long id) {
        return postService.lookupPost(id);
    }
}
