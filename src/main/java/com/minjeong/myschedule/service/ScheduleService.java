package com.minjeong.myschedule.service;

import com.minjeong.myschedule.dto.ScheduleRequestDto;
import com.minjeong.myschedule.dto.ScheduleResponseDto;
import com.minjeong.myschedule.entity.Schedule;
import com.minjeong.myschedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;


    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

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

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()->new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        if(schedule.getPassword().equals(requestDto.getPassword())){
            schedule.update(requestDto);
        }else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }

    @Transactional
    public Long deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("선택한 일정이 존재하지 않습니다."));
        if(schedule.getPassword().equals(requestDto.getPassword())){
            scheduleRepository.delete(schedule);
        }else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return id;
    }
}