package com.example.thedeveloperlife.controller;

import com.example.thedeveloperlife.dto.ApiResponseDto;
import com.example.thedeveloperlife.dto.PostRequestDto;
import com.example.thedeveloperlife.security.UserDetailsImpl;
import com.example.thedeveloperlife.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PutMapping("/post/{id}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id,postRequestDto,userDetails.getUser());
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }
}
