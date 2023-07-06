package com.example.thedeveloperlife.controller;

import com.example.thedeveloperlife.dto.*;
import com.example.thedeveloperlife.security.UserDetailsImpl;
import com.example.thedeveloperlife.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    // 프로필 조회 메서드
    @GetMapping("/{id}")
    @ResponseBody
    public UserResponseDto lookupUser(@PathVariable Long id) {
        return userService.lookupUser(id);
    }

    @GetMapping("/profile")
    public String getPost(Model model,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if(userDetails != null) {
//            model.addAttribute("user", userDetails.getUser().getName());
//        }else {
//            model.addAttribute("user", "Guest");
//        }
        model.addAttribute("user", userDetails.getUser());
        return "profile"; // profile.html view
    }

    @GetMapping("/update")
    public String updatePage(@RequestParam Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws JsonProcessingException {
//        if(!userService.lookupUser(id).getName().equals(userDetails.getUsername())) {
//            /* id에 해당하는 프로필로 이동 */
//            return "redirect:/api/user/profile";
//        }
        model.addAttribute("info_username",userDetails.getUser().getName());
        model.addAttribute("info_user",userService.lookupUser(id));
        return "update";
    }

    // 프로필 수정 메서드
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateRequestDto updateRequestDto) {
        return userService.updateUser(id, updateRequestDto);
    }
}
