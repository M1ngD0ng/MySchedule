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
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    public CommentResponseDto addComment(Long schedule_id, CommentRequestDto commentRequestDto, String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);

        if(!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);

        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 사용자입니다.")); // 이건 어떨때 나오는거지..?


        Schedule schedule = scheduleRepository.findById(schedule_id).orElseThrow(() -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        Comment comment = new Comment(commentRequestDto, schedule, user);
        schedule.addCommentList(comment); // schedule 객체에 댓글 추가

        commentRepository.save(comment);

        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    public CommentResponseDto updateComment(Long schedule_id, Long comment_id, CommentRequestDto commentRequestDto, String authorizationHeader) {
        if (!scheduleRepository.existsById(schedule_id)) {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        } // 일정이 존재하는지 먼저 확인

        String token = jwtUtil.substringToken(authorizationHeader);

        if(!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);

        User user = userRepository.findByUsername(info.getSubject()).orElseThrow( // 토큰으로 사용자 정보 가져옴
                ()-> new IllegalArgumentException("존재하지 않는 사용자입니다.")); // 이건 어떨때 나오는거지..?


        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));

        if(!user.getUsername().equals(comment.getUsername())){
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }
        comment.update(commentRequestDto);
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    public void deleteComment(Long schedule_id, Long comment_id, String authorizationHeader) {
        if (!scheduleRepository.existsById(schedule_id)) {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
        String token = jwtUtil.substringToken(authorizationHeader);

        if(!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);

        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 사용자입니다.")); // 이건 어떨때 나오는거지..?


        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));

        if(!user.getUsername().equals(comment.getUsername())){
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");
        }
        commentRepository.deleteById(comment_id);
    }
}
