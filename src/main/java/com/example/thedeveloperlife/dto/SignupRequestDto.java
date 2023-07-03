package com.example.thedeveloperlife.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank(message = "Id 입력이 안되었습니다.")
    private String name;

    @NotBlank(message = "닉네임 입력이 안되었습니다.")
    private String nickname;

    @NotBlank(message = "비밀번호 입력이 안되었습니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인란 입력이 안되었습니다.")
    private String checkPassword;

    @Email(message = "올바른 이메일이 아닙니다.")
    private String email;

    private String introduce;
}
