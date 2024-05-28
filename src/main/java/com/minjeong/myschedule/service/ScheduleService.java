package com.minjeong.myschedule.service;

import com.minjeong.myschedule.dto.CommentResponseDto;
import com.minjeong.myschedule.dto.ScheduleRequestDto;
import com.minjeong.myschedule.dto.ScheduleResponseDto;
import com.minjeong.myschedule.entity.Schedule;
import com.minjeong.myschedule.entity.User;
import com.minjeong.myschedule.jwt.JwtUtil;
import com.minjeong.myschedule.repository.ScheduleRepository;
import com.minjeong.myschedule.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ScheduleService(ScheduleRepository scheduleRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);

        if(!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);

        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 사용자입니다.")); // 이건 어떨때 나오는거지..?

        Schedule schedule = new Schedule(requestDto, user);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(savedSchedule);
        return responseDto;
    }
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }
    public List<ScheduleResponseDto> getSchedules(){
        return scheduleRepository.findAllByOrderByModifiedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);

        if(!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);

        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 사용자입니다.")); // 이건 어떨때 나오는거지..?

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()->new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));

        if(!user.getUsername().equals(schedule.getUsername())){
            throw new IllegalArgumentException("일정 작성자만 수정할 수 있습니다."); // username이 같아야 수정 가능
        }

        schedule.update(requestDto);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }

    public Long deleteSchedule(Long id, String authorizationHeader) {
        String token = jwtUtil.substringToken(authorizationHeader);

        if(!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        Claims info = jwtUtil.getUserInfoFromToken(token);

        User user = userRepository.findByUsername(info.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 사용자입니다.")); // 이건 어떨때 나오는거지..?


        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));

        if(!user.getUsername().equals(schedule.getUsername())){
            throw new IllegalArgumentException("일정 작성자만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
        return id;
    }

    public List<CommentResponseDto> getScheduleComments(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        List<CommentResponseDto> commentResponseDtos= schedule.getCommentList().stream().map(CommentResponseDto::new).toList();
        return commentResponseDtos;
    }
}