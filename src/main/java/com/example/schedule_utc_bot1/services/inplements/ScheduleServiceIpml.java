package com.example.schedule_utc_bot1.services.inplements;


import com.example.schedule_utc_bot1.domains.Schedule;
import com.example.schedule_utc_bot1.model.Schedule.ScheduleCreateDTO;
import com.example.schedule_utc_bot1.model.Schedule.ScheduleDTO;
import com.example.schedule_utc_bot1.repositories.LessonRepository;
import com.example.schedule_utc_bot1.repositories.ScheduleRepository;
import com.example.schedule_utc_bot1.repositories.StudentRepository;
import com.example.schedule_utc_bot1.services.ScheduleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceIpml implements ScheduleService {
    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public ScheduleDTO findById(int id) throws Exception {
        Optional<Schedule> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception(String.format("Schedule ID %s khônng tồn tại", id));
        }
        ScheduleDTO scheduleDTO = new ScheduleDTO(optional.get());
        return scheduleDTO;
    }

    @Override
    public Integer create(ScheduleCreateDTO dto) throws Exception {
        boolean existSchedule = repository.existsScheduleByStudentCodeAndDate(dto.getStudentCode(), dto.getDate());
        // kiểm tra đã tồn tại Schedule theo studentCode và Date chưa
        if (existSchedule)
            throw new Exception(String.format("Schedule có studentCode[%s]  và Date[%s] đã tồn tại", dto.getStudentCode(), dto.getDate()));
        // kiểm tra StudentCode có tồn tại trong bảng Student ko
        if (!studentRepository.existsByCode(dto.getStudentCode())) return null;
        Schedule schedule = new Schedule(dto);
        return repository.save(schedule).getId();
    }

    @Override
    public boolean update(ScheduleDTO dto) throws Exception {
        Optional<Schedule> optional = repository.findById(dto.getId());
        // kiểm tra Schedule theo ID có tồn tại ko
        if (optional.isEmpty()) {
            throw new Exception(String.format("Schedule ID %s khônng tồn tại", dto.getId()));
        }
        Schedule scheduleDB = optional.get();
        // kiểm tra ScheduleUpdate có đồng thời trùng StudentCode và Date với Schedule trong data ko
        if (repository.existsScheduleByStudentCodeAndDate(dto.getStudentCode(), dto.getDate()) && !dto.getStudentCode().equals(scheduleDB.getStudentCode()) && !dto.getDate().equals(scheduleDB.getDate())) {
            return false;
        }
        Schedule scheduleUpdate = new Schedule(dto);
        repository.save(scheduleUpdate);
        return true;
    }

    @Override
    public void delete(Integer id) throws Exception {
        lessonRepository.deleteAllByScheduleID(id);
        repository.deleteById(id);
    }

    @Override
    public List<ScheduleDTO> findAll() throws Exception {
        List<Schedule> all = repository.findAll();
        if (CollectionUtils.isNotEmpty(all)) {
            List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
            all.forEach(x -> {
                scheduleDTOS.add(new ScheduleDTO(x));
            });
            return scheduleDTOS;
        }
        return null;
    }

    @Override
    public List<ScheduleDTO> findByDate(Date date) {
        List<Schedule> scheduleList = repository.findByDate(date);
        if (CollectionUtils.isNotEmpty(scheduleList)) {
            return scheduleList.stream().map(ScheduleDTO::new).collect(Collectors.toList());
        }
        return null;
    }

    Date convertTimeTo_0h00 (Date date){
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.MILLISECOND, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.HOUR_OF_DAY, 0);
        return day.getTime();
    }

    @Override
    public List<ScheduleDTO> findByDateAndActive(Date date, boolean active) {
        List<Schedule> scheduleList = repository.findByDate(convertTimeTo_0h00(date));

        if (CollectionUtils.isNotEmpty(scheduleList)) {
            List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
            scheduleList.stream().forEach(x -> {
                if (x.isActive() == active) {
                    scheduleDTOS.add(new ScheduleDTO(x));
                }
            });
            return scheduleDTOS;
        }
        return null;
    }

    @Override
    public void updateStatusById(Integer id, boolean isActive) {
        Optional<Schedule> optional = repository.findById(id);
        if (optional.isPresent()) {
            Schedule schedule = optional.get();
            if (schedule.isActive() != isActive) {
                schedule.setActive(isActive);
                repository.save(schedule);
            }
        }
    }
}
