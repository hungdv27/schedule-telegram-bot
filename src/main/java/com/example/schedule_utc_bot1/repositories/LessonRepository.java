package com.example.schedule_utc_bot1.repositories;


import com.example.schedule_utc_bot1.domains.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    void deleteAllByScheduleID(int scheduleID);

    Optional<Lesson> findById(Integer id);

    List<Lesson> findAllByScheduleID(Integer scheduleId);
}
