package com.example.thedeveloperlife.controller;

import com.example.thedeveloperlife.dto.*;
import com.example.thedeveloperlife.security.UserDetailsImpl;
import com.example.thedeveloperlife.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(
            @Valid @RequestBody SignupRequestDto signupRequestDto,
            BindingResult bindingResult) {

        log.info("signup");
        // 1. 예외 처리
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        if(fieldErrorList.size() > 0) {

            // DTO Validation 에 따른 예외 처리 발생 시
            // for 문을 통해 어떤 필드에 어떤 오류인지 message 를 출력합니다.
            // 해당 메시지는 userRequestDto 멩버변수에 Validation 어노테이션에 message 속성을 출력합니다.
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + "필드 : " + fieldError.getDefaultMessage());
            }

            // 아래는 클라이언트 쪽에 400 에러 코드와 에러 메시지를 반환하는 코드입니다.
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"회원가입 실패"));

        } else {

            // DTO Validation 에 따른 예외 처리 미발생 시 -> 회원가입을 진행합니다.
            return userService.signup(signupRequestDto);

        }// end of the if() ~ else() 문
    }// end of this method()


    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto,response);
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout() {
        // 프론트엔드 단을 구성한다면 html 파일명을 반환해야 합니다.
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(),"로그아웃 성공"));
    }

    @GetMapping("/{id}")
    public UserResponseDto lookupUser(@PathVariable Long id) {
        return userService.lookupUser(id);
    }

    // 프로필 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> update(@PathVariable Long id, @RequestBody UpdateRequestDto updateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.update(id, updateRequestDto, userDetails.getUser());
    }

}
