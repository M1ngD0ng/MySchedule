package com.minjeong.myschedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {
    private Long id;
    private String username;

    private String title;
    private String password;

    private String contents;
    private LocalDateTime modifiedAt;

}

