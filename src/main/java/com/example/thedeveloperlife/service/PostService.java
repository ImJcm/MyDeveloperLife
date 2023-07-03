package com.example.thedeveloperlife.service;

import com.example.thedeveloperlife.dto.ApiResponseDto;
import com.example.thedeveloperlife.dto.PostRequestDto;
import com.example.thedeveloperlife.entity.Category;
import com.example.thedeveloperlife.entity.Post;
import com.example.thedeveloperlife.entity.User;
import com.example.thedeveloperlife.repository.CategoryRepository;
import com.example.thedeveloperlife.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j(topic = "게시글 Service")
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseEntity<ApiResponseDto> updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Optional<Post> post = postRepository.findById(id);
        Optional<Category> category = categoryRepository.findById(postRequestDto.getCategory_id());

        if (!post.isPresent() || !Objects.equals(post.get().getUser().getName(), user.getName())) {
            log.error("게시글이 존재하지 않거나 게시글 작성자가 아닙니다.");
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "게시글 수정 실패"));
        }

        if (!category.isPresent()) {
            log.error("해당하는 카테고리가 존재하지 않습니다.");
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "게시글 수정 실패"));
        }

        post.get().update(postRequestDto, category.get());

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(), "게시글 수정 성공",post.get()));
        //return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(), "게시글 수정 성공"));
    }


    @Transactional
    public ResponseEntity<ApiResponseDto> deletePost(Long id, User user) {
        Optional<Post> post = postRepository.findById(id);

        if(!post.isPresent() || !Objects.equals(post.get().getUser().getName(),user.getName())) {
            log.error("게시글이 존재하지 않거나 게시글의 작성자가 아닙니다.");
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "게시글 삭제 실패"));
        }

        postRepository.delete(post.get());

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(), "게시글 삭제 성공"));
    }
}