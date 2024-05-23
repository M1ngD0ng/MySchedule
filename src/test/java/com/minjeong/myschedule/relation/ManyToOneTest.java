package com.minjeong.myschedule.relation;

import com.minjeong.myschedule.entity.Comment;
import com.minjeong.myschedule.entity.Schedule;
import com.minjeong.myschedule.repository.CommentRepository;
import com.minjeong.myschedule.repository.ScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@AutoConfigureMockMvc
class ManyToOneTest {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트")
    void test1() {
        Schedule schedule = new Schedule();
        schedule.setTitle("제목");
        schedule.setContents("내용");
        schedule.setUsername("alswjd6807@naver.com");
        schedule.setPassword("123456");

        Comment comment = new Comment();
        comment.setContents("댓글 내용");
        comment.setUsername("alswjd9999@naver.com");
        comment.setSchedule(schedule);


        Comment comment2 = new Comment();
        comment2.setContents("댓글 내용2");
        comment2.setUsername("alswjd1111@naver.com");
        comment2.setSchedule(schedule);

        scheduleRepository.save(schedule);
        commentRepository.save(comment);
        commentRepository.save(comment2);

    }
}


