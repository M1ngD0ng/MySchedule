package com.minjeong.myschedule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minjeong.myschedule.dto.ScheduleRequestDto;
import com.minjeong.myschedule.entity.Schedule;
import com.minjeong.myschedule.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ScheduleRepository scheduleRepository;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        scheduleRepository.deleteAll();
    }

    @Test
    @DisplayName("createSchedule 기능 테스트")
    public void createSchedule() throws Exception {
        //given
        final String url = "/api/schedules";
        final ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("제목", "내용", "alswjd6807@naver.com", "비번", LocalDateTime.now(), null);

        final String requestBody = objectMapper.writeValueAsString(scheduleRequestDto);

        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList.size()).isEqualTo(1);
        assertThat(scheduleList.get(0).getTitle()).isEqualTo("제목");
        assertThat(scheduleList.get(0).getContents()).isEqualTo("내용");
    }

    @Test
    @DisplayName("getSchedule 기능 테스트")
    public void getSchedule() throws Exception {
        final String url = "/api/schedules/{id}";

        final ScheduleRequestDto requestDto = new ScheduleRequestDto("제목", "내용", "alswjd6807@naver.com", "비번", LocalDateTime.now(), null);
        final Schedule savedSchedule = new Schedule(requestDto);
        scheduleRepository.save(savedSchedule);


        final ResultActions resultActions = mockMvc.perform(get(url, savedSchedule.getId()));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.contents").value("내용"))
                .andExpect(jsonPath("$.username").value("alswjd6807@naver.com"))
                .andExpect(jsonPath("$.password").value("비번"));
    }

    @Test
    @DisplayName("getSchedules 기능 테스트")
    public void getSchedules() throws Exception {
        final String url = "/api/schedules";
        final ScheduleRequestDto requestDto = new ScheduleRequestDto("제목", "내용", "alswjd6807@naver.com", "비번", LocalDateTime.now(), null);
        final Schedule savedSchedule = new Schedule(requestDto);
        scheduleRepository.save(savedSchedule);

        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("제목"))
                .andExpect(jsonPath("$[0].contents").value("내용"))
                .andExpect(jsonPath("$[0].username").value("alswjd6807@naver.com"))
                .andExpect(jsonPath("$[0].password").value("비번"));

    }

    @Test
    @DisplayName("updateSchedule 기능 테스트")
    public void updateSchedule() throws Exception {
        final String url = "/api/schedules/{id}";

        final ScheduleRequestDto requestDto = new ScheduleRequestDto("제목", "내용", "alswjd6807@naver.com", "비번", LocalDateTime.now(), null);
        final Schedule savedSchedule = new Schedule(requestDto);
        scheduleRepository.save(savedSchedule);

        final ScheduleRequestDto updatedRequestDto = new ScheduleRequestDto("제목2", "내용2", "alsdk6807@naver.com", "비번", LocalDateTime.now(), null);
        final String requestBody = objectMapper.writeValueAsString(updatedRequestDto);

        mockMvc.perform(post(url, savedSchedule.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));


        Schedule schedule = scheduleRepository.findById(savedSchedule.getId()).get();

        assertThat(schedule.getTitle()).isEqualTo("제목2");
        assertThat(schedule.getContents()).isEqualTo("내용2");
        assertThat(schedule.getUsername()).isEqualTo("alsdk6807@naver.com");
        assertThat(schedule.getPassword()).isEqualTo("비번");
    }

    @Test
    @DisplayName("deleteSchedule 기능 테스트")
    public void deleteSchedule() throws Exception {
        final String url = "/api/schedules/{id}";

        final ScheduleRequestDto requestDto = new ScheduleRequestDto("제목", "내용", "alswjd6807@naver.com", "비번", LocalDateTime.now(), null);
        final Schedule savedSchedule = new Schedule(requestDto);
        scheduleRepository.save(savedSchedule);

        final String requestBody = objectMapper.writeValueAsString(requestDto);
        mockMvc.perform(delete(url, savedSchedule.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).isEmpty();

    }
}