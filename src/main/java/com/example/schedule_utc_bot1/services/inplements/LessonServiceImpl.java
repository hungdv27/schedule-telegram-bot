package com.example.schedule_utc_bot1.services.inplements;


import com.example.schedule_utc_bot1.domains.Lesson;
import com.example.schedule_utc_bot1.model.Lesson.LessonCreateDTO;
import com.example.schedule_utc_bot1.model.Lesson.LessonDTO;
import com.example.schedule_utc_bot1.repositories.LessonRepository;
import com.example.schedule_utc_bot1.repositories.ScheduleRepository;
import com.example.schedule_utc_bot1.services.LessonService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository repository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public LessonDTO findById(int id) throws Exception {
        Optional<Lesson> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception(String.format("Leson ID %s khônng tồn tại", id));
        }
        LessonDTO lessonDTO = new LessonDTO(optional.get());
        return lessonDTO;
    }

    @Override
    public Integer create(LessonCreateDTO dto) throws Exception {
        boolean existsSchdule = scheduleRepository.existsById(dto.getScheduleID());
        if (!existsSchdule) {
            throw new Exception(String.format("ScheduleID %s không tồn tại [Lesson Service create]",
                    dto.getScheduleID()));
        }
        return repository.save(new Lesson(dto)).getId();
    }

    @Override
    public boolean update(LessonDTO dto) throws Exception {
        Optional<Lesson> optional = repository.findById(dto.getScheduleID());
        if (optional.isPresent()) {
            if (scheduleRepository.existsById(dto.getScheduleID())) {
                Lesson lesson = optional.get();
                lesson.setLessonNum(dto.getLessonNum());
                lesson.setActive(dto.isActive());
                lesson.setAddress(dto.getAddress());
                lesson.setScheduleID(dto.getScheduleID());
                repository.save(lesson);
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(Integer id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<LessonDTO> findAll() throws Exception {
        List<Lesson> all = repository.findAll();
        if (CollectionUtils.isNotEmpty(all)) {
            List<LessonDTO> lessonDTOS = new ArrayList<>();
            all.forEach(x -> {
                lessonDTOS.add(new LessonDTO(x));
            });
            return lessonDTOS;
        }
        return null;
    }

    @Override
    public List<LessonDTO> findAllBySchduleID(Integer scheduleID) {
        List<Lesson> allByScheduleID = repository.findAllByScheduleID(scheduleID);
        if(CollectionUtils.isNotEmpty(allByScheduleID)){
            return allByScheduleID.stream().map(LessonDTO::new).collect(Collectors.toList());
        }
        return null;
    }
}
