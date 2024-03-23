package com.example.thedeveloperlife.controller;

import com.example.thedeveloperlife.dto.ApiResponseDto;
import com.example.thedeveloperlife.dto.PostRequestDto;
import com.example.thedeveloperlife.dto.PostResponseDto;
import com.example.thedeveloperlife.entity.User;
import com.example.thedeveloperlife.security.UserDetailsImpl;
import com.example.thedeveloperlife.service.PostService;
import com.example.thedeveloperlife.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/post")
    @ResponseBody
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
      return postService.createPost(requestDto, userDetails.getUser());
    }
    @GetMapping("/post/write")
    public String createPostView(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("info_username", userDetails.getUser().getName());
        return "writePost";
    }

    @GetMapping("/post/modify")
    public String modifyPostView(@RequestParam Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        User writer = userService.findUser(postService.lookupPost(id).getUserName());
        if(!writer.getName().equals(userDetails.getUsername())) {
            /* 게시글 작성자가 아닐 시, id에 해당하는 게시글 페이지로 이동 */
            return "redirect:/api/post-page/"+id;
        }
        model.addAttribute("info_username",userDetails.getUser().getName());
        model.addAttribute("info_post",postService.lookupPost(id));
        return "modifyPost";
    }
  
    @GetMapping("/posts")
    @ResponseBody
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/posts/{category_id}")
    public ResponseEntity<ApiResponseDto> getCategoryPosts(@PathVariable Long category_id) {
        return postService.getCategoryPosts(category_id);
    }

    @GetMapping("/post-page/{id}")
    public String getPost(@PathVariable Long id,
                          Model model,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.lookupPost(id);
        if(userDetails != null) {
            model.addAttribute("user", userDetails.getUser().getName());
        }else {
            model.addAttribute("user", "Guest");
        }
        model.addAttribute("post", responseDto);
        model.addAttribute("commentList", responseDto.getCommentResponseDtoList());

        return "postDetail"; // postDetail.html view
    }

    @GetMapping("/post/{id}")
    @ResponseBody
    public PostResponseDto lookupPost(@PathVariable Long id) {
        return postService.lookupPost(id);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id,postRequestDto,userDetails.getUser());
    }

    @DeleteMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getUser());
    }


}
