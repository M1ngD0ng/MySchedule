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

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "username", nullable = false)
    private String username;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
        this.createdAt = LocalDateTime.now();
    }
    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
        this.modifiedAt = LocalDateTime.now();
    }
}
