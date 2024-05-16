package com.minjeong.myschedule.controller;

import com.minjeong.myschedule.dto.ScheduleRequestDto;
import com.minjeong.myschedule.dto.ScheduleResponseDto;
import com.minjeong.myschedule.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final Map<Long, Schedule> schedulelist= new HashMap<>();

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule= new Schedule(requestDto);
        Long maxId = schedulelist.size() > 0 ? Collections.max(schedulelist.keySet()) + 1 : 1;
        schedule.setId(maxId);

        schedulelist.put(schedule.getId(), schedule);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        List<ScheduleResponseDto> responseList = schedulelist.values().stream()
                .map(ScheduleResponseDto::new).toList();
        return responseList;
    }

    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        if(schedulelist.containsKey(id)) {
            Schedule schedule = schedulelist.get(id);

            schedule.update(requestDto);
            return schedule.getId();
        } else{
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        if(schedulelist.containsKey(id)) {
            schedulelist.remove(id);
            return id;
        }else {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }
}
