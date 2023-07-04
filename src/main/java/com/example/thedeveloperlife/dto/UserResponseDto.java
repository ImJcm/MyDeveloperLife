package com.example.thedeveloperlife.dto;

import com.example.thedeveloperlife.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String introduce;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.introduce = user.getIntroduce();
    }
}
