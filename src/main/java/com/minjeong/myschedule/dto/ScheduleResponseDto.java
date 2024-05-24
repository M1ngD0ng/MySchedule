package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Comment;
import com.minjeong.myschedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final String username;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private final List<CommentResponseDto> commentList;

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