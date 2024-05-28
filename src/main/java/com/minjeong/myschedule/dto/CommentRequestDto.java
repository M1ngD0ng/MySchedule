package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Schedule;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
    @NotBlank
    private String contents;

    @Email
    private String username;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @NotBlank
    private Schedule schedule;

}
