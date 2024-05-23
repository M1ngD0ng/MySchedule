package com.minjeong.myschedule.service;

import com.minjeong.myschedule.dto.CommentRequestDto;
import com.minjeong.myschedule.dto.CommentResponseDto;
import com.minjeong.myschedule.entity.Comment;
import com.minjeong.myschedule.entity.Schedule;
import com.minjeong.myschedule.repository.CommentRepository;
import com.minjeong.myschedule.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    public CommentService(ScheduleRepository scheduleRepository, CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public CommentResponseDto addComment(Long schedule_id, CommentRequestDto commentRequestDto) {
        Schedule schedule = scheduleRepository.findById(schedule_id).orElseThrow(() -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        Comment comment = new Comment(commentRequestDto);
        schedule.addCommentList(comment); // schedule 객체에 댓글 추가

        commentRepository.save(comment);

        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    public CommentResponseDto updateComment(Long schedule_id, Long comment_id, CommentRequestDto commentRequestDto) {
        if (!scheduleRepository.existsById(schedule_id)) {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }

        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));

        if (comment.getUsername().equals(commentRequestDto.getUsername())) {
            comment.update(commentRequestDto);
        } else {
            throw new IllegalArgumentException("선택한 댓글의 작성자가 아닙니다.");
        }
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    public void deleteComment(Long schedule_id, Long comment_id, CommentRequestDto commentRequestDto) {
        if (!scheduleRepository.existsById(schedule_id)) {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));

        if (comment.getUsername().equals(commentRequestDto.getUsername())) {
            commentRepository.deleteById(comment_id);
        } else {
            throw new IllegalArgumentException("선택한 댓글의 작성자가 아닙니다.");
        }
    }
}
