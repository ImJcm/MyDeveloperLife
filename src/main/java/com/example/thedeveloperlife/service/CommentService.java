package com.example.thedeveloperlife.service;

import com.example.thedeveloperlife.dto.ApiResponseDto;
import com.example.thedeveloperlife.dto.CommentRequestDto;
import com.example.thedeveloperlife.dto.CommentResponseDto;
import com.example.thedeveloperlife.entity.Category;
import com.example.thedeveloperlife.entity.Comment;
import com.example.thedeveloperlife.entity.Post;
import com.example.thedeveloperlife.entity.User;
import com.example.thedeveloperlife.repository.CategoryRepository;
import com.example.thedeveloperlife.repository.CommentRepository;
import com.example.thedeveloperlife.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j(topic = "댓글 Service")
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto createComment(Long post_id, CommentRequestDto requestDto, User user) {
        // 해당 게시글이 있는지
        Optional<Post> post = postRepository.findById(post_id);

        // 해당 게시글이 없을 경우
        if(!post.isPresent()) {
            log.error("해당 게시글이 없습니다.");
            return null;
        }

        // 해당 게시글이 있을 경우
        Comment comment = new Comment(requestDto,post.get(),user);

        // DB에 댓글 저장.
        commentRepository.save(comment);
        log.info("댓글 저장 완료");
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {
        // 댓글이 있는지 여부 판단
        Optional<Comment> checkComment = commentRepository.findById(id);
        // 해당 댓글이 없을 경우
        if(!checkComment.isPresent()){
            log.error("해당 댓글이 없습니다.");
            return null;
        }

        // 사용자가 쓴 글 인지 판단
        Comment comment = checkComment.get();
        Long commentUserId = comment.getUser().getId();
        // 댓글을 쓴 사용자가 아닐 경우
        if(!commentUserId.equals(user.getId())){
            log.error("해당 댓글의 작성자가 아닙니다.");
            return null;
        }

        // 댓글 수정하기
        comment.update(requestDto);
        log.info("댓글 수정 완료");
        return new CommentResponseDto(comment);
    }

    public ResponseEntity<ApiResponseDto> deleteComment(Long id, User user) {
        // 해당 댓글 존재 여부 판단
        Optional<Comment> checkComment = commentRepository.findById(id);
        // 해당 댓글이 없을 경우
        if(!checkComment.isPresent()){
            log.error("해당 댓글이 없습니다.");
            return null;
        }

        // 해당 댓글 작성자인지 판단
        Comment comment = checkComment.get();
        Long commentUserId = comment.getUser().getId();
        if(!commentUserId.equals(user.getId())){
            log.error("해당 댓글의 작성자가 아닙니다.");
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "댓글 삭제 실패, 댓글 작성자가 아닙니다."));
            //return null;
        }

        // 해당 댓글 삭제
        commentRepository.delete(comment);
        log.info("댓글 삭제 완료");
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(), "댓글 삭제 성공"));
    }

    public List<CommentResponseDto> getComments(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPostIdOrderByCreatedAt(postId);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        //익명 게시판의 게시글이라면 댓글 또한 작성자를 "익명"으로 전환 필요
        if(postRepository.findById(postId).get().getCategory().getId() == 7) {
            for (Comment comment : commentList) {
                CommentResponseDto newCommentResponseDto = new CommentResponseDto(comment);
                newCommentResponseDto.setUserName("익명");
                newCommentResponseDto.setUserNickname("익명");
                commentResponseDtoList.add(new CommentResponseDto(comment));
            }
        } else {
            for (Comment comment : commentList) {
                commentResponseDtoList.add(new CommentResponseDto(comment));
            }
        }
        return commentResponseDtoList;
    }
}
