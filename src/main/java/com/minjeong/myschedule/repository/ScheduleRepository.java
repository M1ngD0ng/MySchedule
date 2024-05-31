package com.minjeong.myschedule.repository;

import com.minjeong.myschedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByModifiedAtDesc();
    Optional<Schedule> findById(Long id);

    void deleteScheduleByUserIdAndId(Long schduleId, Long userId);
}