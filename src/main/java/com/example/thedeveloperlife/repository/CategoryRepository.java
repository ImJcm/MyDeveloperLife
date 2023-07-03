package com.example.thedeveloperlife.repository;

import com.example.thedeveloperlife.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
