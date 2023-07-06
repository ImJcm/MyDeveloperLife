package com.example.thedeveloperlife.repository;

import com.example.thedeveloperlife.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByCreatedAt(Long id);
}
