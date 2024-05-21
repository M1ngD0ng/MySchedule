package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
//
//    public ScheduleRequestDto(Schedule schedule) {
//        this.id=schedule.getId();
//        this.title=schedule.getTitle();
//        this.contents=schedule.getContents();
//        this.username=schedule.getUsername();
//        this.password=schedule.getPassword();
//        this.createdAt=schedule.getCreatedAt();
//        this.modifiedAt=schedule.getModifiedAt();
//    }
}
