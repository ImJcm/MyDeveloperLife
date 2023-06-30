package com.example.mydeveloperlife.repository;

import com.example.mydeveloperlife.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}

