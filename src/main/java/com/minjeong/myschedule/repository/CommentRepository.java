package com.minjeong.myschedule.repository;

import com.minjeong.myschedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    void deleteCommentByUserIdAndId(Long userId, Long id);
}
