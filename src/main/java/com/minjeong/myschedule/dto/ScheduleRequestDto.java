package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Schedule;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
    private Long id;

    @NotNull
    @Size(min=1, max=200)
    private String title;
    private String contents;

    @Email
    private String username;

    @NotNull
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
