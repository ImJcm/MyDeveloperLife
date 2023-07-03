package com.example.thedeveloperlife.repository;

import com.example.thedeveloperlife.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByName(String inputName);
}
