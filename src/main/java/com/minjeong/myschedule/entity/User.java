package com.minjeong.myschedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private String password;

//    @Column(nullable = false, unique = true)
//    private String email;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;



    public User(String nickname, String username, String password, UserRoleEnum role) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }
}