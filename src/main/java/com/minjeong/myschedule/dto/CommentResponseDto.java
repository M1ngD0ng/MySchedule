package com.minjeong.myschedule.dto;

import com.minjeong.myschedule.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String contents;
    private final String nickname;

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private final Long schedule_id;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.nickname = comment.getNickname();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.schedule_id= comment.getSchedule().getId();
    }
}