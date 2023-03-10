package com.example.schedule_utc_bot1.repositories;


import com.example.schedule_utc_bot1.domains.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    boolean existsScheduleByStudentCodeAndDate(String studentCode, Date date);

    Optional<Schedule> findScheduleByStudentCodeAndDate(String studentCode, Date date);

    boolean existsSchedulesById(Integer id);

    List<Schedule> findByDate(Date date);



}
