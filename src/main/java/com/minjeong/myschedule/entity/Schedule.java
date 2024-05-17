package com.minjeong.myschedule.entity;

import com.minjeong.myschedule.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    private Long id;

    private String title;
    private String password;

    private String username;
    private String contents;
    private LocalDateTime modifiedAt;

    public Schedule(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.modifiedAt = LocalDateTime.now();
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.modifiedAt = LocalDateTime.now();
        this.title = requestDto.getTitle();
    }
}