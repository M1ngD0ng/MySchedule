package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Schedule;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
    private String contents;

    @Email
    private String username;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Schedule schedule;

}
