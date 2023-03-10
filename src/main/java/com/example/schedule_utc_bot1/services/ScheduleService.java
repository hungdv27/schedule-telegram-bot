package com.example.schedule_utc_bot1.services;


import com.example.schedule_utc_bot1.model.Schedule.ScheduleCreateDTO;
import com.example.schedule_utc_bot1.model.Schedule.ScheduleDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ScheduleService {
    ScheduleDTO findById(int id) throws Exception;

    Integer create(ScheduleCreateDTO dto) throws Exception;

    boolean update(ScheduleDTO dto) throws Exception;

    void delete(Integer id) throws Exception;

    List<ScheduleDTO> findAll() throws Exception;

    List<ScheduleDTO> findByDate(Date date);

    List<ScheduleDTO> findByDateAndActive(Date date, boolean active);

    void updateStatusById(Integer id, boolean isActive);
}
