package com.example.mydeveloperlife.service;

import com.example.mydeveloperlife.dto.PostRequestDto;
import com.example.mydeveloperlife.dto.PostResponseDto;
import com.example.mydeveloperlife.entity.Post;
import com.example.mydeveloperlife.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        // RequestDto -> Entity
        Post post = new Post(requestDto);

        post.setUser(user);

        // DB 저장
        Post savePost = postRepository.save(post);

        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto lookupPost(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시물은 존재하지 않습니다."));
    }
}
