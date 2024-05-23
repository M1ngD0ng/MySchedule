package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String contents;
    private String username;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Long schedule_id;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.username = comment.getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.schedule_id= comment.getSchedule().getId();
    }
}