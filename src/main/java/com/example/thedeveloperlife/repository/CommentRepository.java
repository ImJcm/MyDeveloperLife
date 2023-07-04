package com.example.thedeveloperlife.repository;

import com.example.thedeveloperlife.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
