package com.minjeong.myschedule.service;

import com.minjeong.myschedule.dto.CommentResponseDto;
import com.minjeong.myschedule.dto.ScheduleRequestDto;
import com.minjeong.myschedule.dto.ScheduleResponseDto;
import com.minjeong.myschedule.entity.Schedule;
import com.minjeong.myschedule.entity.User;
import com.minjeong.myschedule.jwt.JwtUtil;
import com.minjeong.myschedule.repository.ScheduleRepository;
import com.minjeong.myschedule.repository.UserRepository;
import com.minjeong.myschedule.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ScheduleService(ScheduleRepository scheduleRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Schedule schedule = new Schedule(requestDto, userDetails.getUser());

        Schedule savedSchedule = scheduleRepository.save(schedule);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(savedSchedule);
        return responseDto;
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getSchedules(){
        return scheduleRepository.findAllByOrderByModifiedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()->new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));

        if(!userDetails.getUser().getNickname().equals(schedule.getNickname())){
            throw new IllegalArgumentException("일정 작성자만 수정할 수 있습니다."); // nickname이 같아야 수정 가능
        }

        schedule.update(requestDto);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }

    @Transactional
    public Long deleteSchedule(Long id) {

        // 사용자 정보 추출
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        scheduleRepository.deleteScheduleByUserIdAndId(userDetails.getUser().getId(), id);

        return id;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getScheduleComments(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        List<CommentResponseDto> commentResponseDtos= schedule.getCommentList().stream().map(CommentResponseDto::new).toList();
        return commentResponseDtos;
    }
}