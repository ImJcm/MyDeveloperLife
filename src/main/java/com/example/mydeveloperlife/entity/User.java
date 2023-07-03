package com.example.mydeveloperlife.entity;

import com.example.mydeveloperlife.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String introduce;

    public User(SignupRequestDto signupRequestDto, String password) {
        this.name = signupRequestDto.getName();
        this.nickname = signupRequestDto.getNickname();
        this.password = password;
        this.email = signupRequestDto.getEmail();
        this.introduce = signupRequestDto.getIntroduce();
    }
}
