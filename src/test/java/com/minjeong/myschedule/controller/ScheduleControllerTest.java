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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        this.mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        scheduleRepository.deleteAll();
    }

    @Test
    @DisplayName("createSchedule 기능 테스트")
    public void createSchedule() throws Exception {
        //given
        final String url = "/api/schedules";
        final ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto( 10L,"fkfkfkfkfk", "내용입니다.", "alswjd@naver.com", "gkgk369", LocalDateTime.now(), null);

        final String requestBody = objectMapper.writeValueAsString(scheduleRequestDto);

        //when
        ResultActions resultActions=mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        List<Schedule> scheduleList=scheduleRepository.findAll();
        assertThat(scheduleList.size()).isEqualTo(1);
        assertThat(scheduleList.get(0).getTitle()).isEqualTo("fkfkfkfkfk");
        assertThat(scheduleList.get(0).getContents()).isEqualTo("내용입니다.");


    }

}
;