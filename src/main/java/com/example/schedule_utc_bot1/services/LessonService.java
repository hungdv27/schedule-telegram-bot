package com.example.schedule_utc_bot1.services;


import com.example.schedule_utc_bot1.model.Lesson.LessonCreateDTO;
import com.example.schedule_utc_bot1.model.Lesson.LessonDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LessonService {
    LessonDTO findById(int id) throws Exception;

    Integer create(LessonCreateDTO dto) throws Exception;

    boolean update(LessonDTO dto) throws Exception;

    void delete(Integer id) throws Exception;

    List<LessonDTO> findAll() throws Exception;

    List<LessonDTO> findAllBySchduleID(Integer scheduleID);
}
