package com.example.thedeveloperlife.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto{

    @NotBlank(message = "공백은 혀용하지 않습니다.")
    private String Content;
}
