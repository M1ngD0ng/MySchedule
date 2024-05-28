package com.minjeong.myschedule.controller;

import com.minjeong.myschedule.dto.CommentRequestDto;
import com.minjeong.myschedule.dto.CommentResponseDto;
import com.minjeong.myschedule.dto.ScheduleResponseDto;
import com.minjeong.myschedule.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/schedules/{id}/comments")
    public CommentResponseDto addComment(@PathVariable("id") Long schedule_id, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        return commentService.addComment(schedule_id, commentRequestDto);
    }

    @PostMapping("/schedules/{schedule_id}/comments/{comment_id}")
    public CommentResponseDto updateComment(@PathVariable("schedule_id") Long schedule_id, @PathVariable("comment_id") Long comment_id, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        return commentService.updateComment(schedule_id, comment_id, commentRequestDto);
    }

    @DeleteMapping("/schedules/{schedule_id}/comments/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable("schedule_id") Long schedule_id, @PathVariable("comment_id") Long comment_id, @RequestBody CommentRequestDto commentRequestDto){
        try {
            commentService.deleteComment(schedule_id, comment_id, commentRequestDto);
            return ResponseEntity.ok("댓글 삭제가 성공적으로 수행되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
