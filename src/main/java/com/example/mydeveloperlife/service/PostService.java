package com.example.mydeveloperlife.service;

import com.example.mydeveloperlife.dto.PostRequestDto;
import com.example.mydeveloperlife.dto.PostResponseDto;
import com.example.mydeveloperlife.entity.Category;
import com.example.mydeveloperlife.entity.Post;
import com.example.mydeveloperlife.entity.User;
import com.example.mydeveloperlife.repository.CategoryRepository;
import com.example.mydeveloperlife.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {

        Optional<Category> category = categoryRepository.findById(requestDto.getCategory_id());

        // RequestDto -> Entity
        Post post = new Post(requestDto, user, category.get());

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
