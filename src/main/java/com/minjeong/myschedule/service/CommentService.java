package com.minjeong.myschedule.service;

import com.minjeong.myschedule.dto.CommentRequestDto;
import com.minjeong.myschedule.dto.CommentResponseDto;
import com.minjeong.myschedule.entity.Comment;
import com.minjeong.myschedule.entity.Schedule;
import com.minjeong.myschedule.entity.User;
import com.minjeong.myschedule.jwt.JwtUtil;
import com.minjeong.myschedule.repository.CommentRepository;
import com.minjeong.myschedule.repository.ScheduleRepository;
import com.minjeong.myschedule.repository.UserRepository;
import com.minjeong.myschedule.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public CommentService(ScheduleRepository scheduleRepository, CommentRepository commentRepository, UserRepository userRepository ,JwtUtil jwtUtil) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public CommentResponseDto addComment(Long schedule_id, CommentRequestDto commentRequestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Schedule schedule = scheduleRepository.findById(schedule_id).orElseThrow(() -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        Comment comment = new Comment(commentRequestDto, schedule, userDetails.getUser());
        schedule.addCommentList(comment); // schedule 객체에 댓글 추가

        commentRepository.save(comment);

        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long schedule_id, Long comment_id, CommentRequestDto commentRequestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));

        if(!userDetails.getUser().getNickname().equals(comment.getNickname())){
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }
        comment.update(commentRequestDto);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    @Transactional
    public void deleteComment(Long schedule_id, Long comment_id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));

        if(!userDetails.getUser().getNickname().equals(comment.getNickname())){
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");
        }
        commentRepository.deleteCommentByUserIdAndId(comment_id, userDetails.getUser().getId());
    }
}
