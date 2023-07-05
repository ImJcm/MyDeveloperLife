package com.example.thedeveloperlife.controller;

import com.example.thedeveloperlife.service.UserService;
import com.example.thedeveloperlife.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    } // login.html view

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    } // signup.html view

    @PostMapping("/signup")
    public String signup(
            @Valid SignupRequestDto signupRequestDto,
            BindingResult bindingResult) {

        log.info("signup");
        // 1. 예외 처리
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        if(fieldErrorList.size() > 0) {
            // DTO Validation 에 따른 예외 처리 발생 시 for 문을 통해 어떤 필드에 어떤 오류인지 message 를 출력합니다.
            // 해당 메시지는 userRequestDto 멩버변수에 Validation 어노테이션에 message 속성을 출력합니다.
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + "필드 : " + fieldError.getDefaultMessage());
            }
            // 회원가입 페이지 다시 로딩합니다.
            return "redirect:/api/user/signup";
        }
        // 2. 회원가입을 진행합니다.
        userService.signup(signupRequestDto);

        return "redirect:/api/user/login-page";
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        userService.login(requestDto,response);
    }

}
