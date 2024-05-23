package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Comment;
import com.minjeong.myschedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentList;

    public ScheduleResponseDto(Schedule schedule) {
        this.id=schedule.getId();
        this.title=schedule.getTitle();
        this.contents=schedule.getContents();
        this.username=schedule.getUsername();
        this.password=schedule.getPassword();
        this.createdAt=schedule.getCreatedAt();
        this.modifiedAt=schedule.getModifiedAt();

        this.commentList=schedule.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}