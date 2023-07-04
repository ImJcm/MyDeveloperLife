package com.example.thedeveloperlife.controller;

import com.example.thedeveloperlife.dto.ApiResponseDto;
import com.example.thedeveloperlife.dto.CommentRequestDto;
import com.example.thedeveloperlife.dto.CommentResponseDto;
import com.example.thedeveloperlife.security.UserDetailsImpl;
import com.example.thedeveloperlife.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/post/{id}/comment")
    public CommentResponseDto createComment(
            @PathVariable Long id, // 게시글 id
            @RequestBody CommentRequestDto requestDto, // 댓글 내용
            @AuthenticationPrincipal UserDetailsImpl userDetails // 로그인한 유저 정보
            ) {
        return commentService.createComment(id,requestDto,userDetails.getUser());
    }


    // 댓글 수정
    @PutMapping("/post/comment/{id}")
    public CommentResponseDto updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        return commentService.updateComment(id,requestDto,userDetails.getUser());
    }


    // 댓글 삭제
    @DeleteMapping("/post/comment/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        return commentService.deleteComment(id,userDetails.getUser());
    }
}
