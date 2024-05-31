package com.minjeong.myschedule.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Size(min = 4, max = 10, message = "username은 최소 4자 이상, 10자 이하로 입력해주세요.")
    @Pattern(regexp = "[a-z0-9]+", message = "username에는 영문 소문자와 숫자만 사용 가능합니다.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15,  message = "password는 최소 8자 이상, 15자 이하로 입력해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "password에는 영문 대소문자와 숫자만 사용 가능합니다.")
    private String password;

    @NotBlank
    @Size(min = 4, max = 10, message = "nickname은 최소 4자 이상, 10자 이하로 입력해주세요.")
    private String nickname;

    private boolean admin = false;
    private String adminToken = "";
}