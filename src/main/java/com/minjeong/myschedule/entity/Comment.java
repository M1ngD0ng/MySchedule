package com.minjeong.myschedule.entity;

import com.minjeong.myschedule.dto.CommentRequestDto;
import com.minjeong.myschedule.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(CommentRequestDto requestDto, Schedule schedule, User user){
        this.contents = requestDto.getContents();
        this.nickname = user.getNickname();
        this.createdAt = LocalDateTime.now();
        this.schedule = schedule;
        this.user = user;
    }
    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
        this.modifiedAt = LocalDateTime.now();
    }
}
