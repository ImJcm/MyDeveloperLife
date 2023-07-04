package com.example.thedeveloperlife.service;

import com.example.thedeveloperlife.dto.*;
import com.example.thedeveloperlife.entity.Post;
import com.example.thedeveloperlife.entity.User;
import com.example.thedeveloperlife.jwt.JwtUtil;
import com.example.thedeveloperlife.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j(topic = "User Service")
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto signupRequestDto){
        // 1. 입력받은 id와 password 를 저장합니다.
        // password 는 암호화가 이뤄집니다.
        String inputName = signupRequestDto.getName();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 2. user 테이블에 입력받은 id와 동일한 데이터가 있는지 확인합니다.
        Optional<User> checkUser = userRepository.findByName(inputName);

        // 중복 회원이 있을 경우
        if(checkUser.isPresent()) {
            // 서버 측에 로그를 찍는 역할을 합니다.
            log.error(inputName + "와 중복된 사용자가 존재합니다.");

            // 아래는 클라이언트 쪽에 400 에러 코드와 에러 메시지를 반환하는 코드입니다.
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"회원 가입 실패"));
        }

        // 중복 회원이 없을 경우
        User user = new User(signupRequestDto,password);
        userRepository.save(user);

        // 서버 측에 로그를 찍는 역할을 합니다.
        log.info(inputName + "님이 회원 가입에 성공하였습니다");

        // 아래는 클라이언트 쪽에 201 상태 코드와 성공 메시지를 반환하는 코드입니다.
        return ResponseEntity.status(201).body(new ApiResponseDto(HttpStatus.CREATED.value(),"회원 가입 성공"));
    }


    // 로그인 메서드
    public ResponseEntity<ApiResponseDto> login(LoginRequestDto requestDto, HttpServletResponse response) {
        // 클라이언트로 부터 전달 받은 id 와 password 를 가져옵니다.
        String inputName = requestDto.getName();
        String password = requestDto.getPassword();

        // 사용자를 확인하고, 비밀번호를 확인합니다.
        Optional<User> checkUser = userRepository.findByName(inputName);

        // DB에 없는 사용자인 경우 혹은 비밀번호가 일치하지 않을 경우
        if(!checkUser.isPresent() || !passwordEncoder.matches(password,checkUser.get().getPassword())) {
            // 서버 측에 로그를 찍는 역할을 합니다.
            log.error("로그인 정보가 일치하지 않습니다.");

            // 아래는 클라이언트 쪽에 400 에러 코드와 에러 메시지를 반환하는 코드입니다.
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"로그인 실패"));
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가..
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(requestDto.getName()));

        // 서버 측에 로그를 찍는 역할을 합니다.
        log.info(inputName + "님이 로그인에 성공하였습니다");

        // 아래는 클라이언트 쪽에 201 상태 코드와 성공 메시지를 반환하는 코드입니다.
        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(),"로그인 성공"));
    }

    public UserResponseDto lookupUser(Long id) {
        User user = findUser(id);
        return new UserResponseDto(user);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> update(Long id, UpdateRequestDto updateRequestDto, User user) {
        Optional<User> updateduser = userRepository.findById(id);

        if (!updateRequestDto.getPassword().equals(updateRequestDto.getCheckPassword())) {
            log.error("비밀번호가 일치하지 않습니다");
            return ResponseEntity.status(400).body(new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "프로필 수정 실패"));
        }

        updateduser.get().update(updateRequestDto);

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(), "프로필 수정 성공",updateduser));
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시물은 존재하지 않습니다."));
    }
}
