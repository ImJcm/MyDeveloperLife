package com.example.thedeveloperlife.entity;

import com.example.thedeveloperlife.dto.SignupRequestDto;
import com.example.thedeveloperlife.dto.UpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
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

    public void update(UpdateRequestDto updateRequestDto) {
        this.nickname = updateRequestDto.getNickname();
        this.password = updateRequestDto.getPassword();
        this.email = updateRequestDto.getEmail();
        this.introduce = updateRequestDto.getIntroduce();
    }
}
