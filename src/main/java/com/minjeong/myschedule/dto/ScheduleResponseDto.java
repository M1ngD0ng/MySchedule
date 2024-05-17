package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Schedule;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String username;

    private String title;
    private String password;

    private String contents;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId(); // 이거 안해서 delete, update 에러남..
        this.username=schedule.getUsername();
        this.contents=schedule.getContents();
        this.modifiedAt=schedule.getModifiedAt();
        this.title=schedule.getTitle();
    }

}

