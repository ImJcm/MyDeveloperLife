package com.example.thedeveloperlife.controller;

import com.example.thedeveloperlife.dto.ApiResponseDto;
import com.example.thedeveloperlife.dto.LoginRequestDto;
import com.example.thedeveloperlife.dto.UpdateRequestDto;
import com.example.thedeveloperlife.dto.UserResponseDto;
import com.example.thedeveloperlife.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    // 프로필 조회
    @GetMapping("/{id}")
    public UserResponseDto lookupUser(@PathVariable Long id) {
        return userService.lookupUser(id);
    }

    // 프로필 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateRequestDto updateRequestDto) {
        return userService.updateUser(id, updateRequestDto);
    }
}
