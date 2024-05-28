package com.minjeong.myschedule.controller;

import com.minjeong.myschedule.dto.CommentResponseDto;
import com.minjeong.myschedule.dto.ScheduleRequestDto;
import com.minjeong.myschedule.dto.ScheduleResponseDto;
import com.minjeong.myschedule.entity.Comment;
import com.minjeong.myschedule.entity.Schedule;
import com.minjeong.myschedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto, @RequestHeader("Authorization") String authorizationHeader) {
        return scheduleService.createSchedule(scheduleRequestDto, authorizationHeader);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleService.getSchedules();
    }

    @PostMapping("/schedules/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDto scheduleRequestDto, @RequestHeader("Authorization") String authorizationHeader) {
        return scheduleService.updateSchedule(id, scheduleRequestDto, authorizationHeader);
    }

    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        return scheduleService.deleteSchedule(id, authorizationHeader);
    }

    @GetMapping("/schedules/{id}/comments")
    public List<CommentResponseDto> getScheduleComments(@PathVariable Long id) {
        return scheduleService.getScheduleComments(id);
    }
}