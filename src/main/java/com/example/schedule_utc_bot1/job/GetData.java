package com.example.schedule_utc_bot1.job;

import com.example.schedule_utc_bot1.model.ExcelDTO.ScheduleExcelDTO;
import com.example.schedule_utc_bot1.model.ExcelDTO.StudentExcelDTO;
import com.example.schedule_utc_bot1.model.Lesson.LessonCreateDTO;
import com.example.schedule_utc_bot1.model.Schedule.ScheduleCreateDTO;
import com.example.schedule_utc_bot1.model.Student.StudentCreateDTO;
import com.example.schedule_utc_bot1.services.LessonService;
import com.example.schedule_utc_bot1.services.ScheduleService;
import com.example.schedule_utc_bot1.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class GetData {
    @Autowired
    final StudentService studentService;
    @Autowired
    final ScheduleService scheduleService;
    @Autowired
    final LessonService lessonService;

    public GetData(StudentService studentService, ScheduleService scheduleService, LessonService lessonService) {
        this.studentService = studentService;
        this.scheduleService = scheduleService;
        this.lessonService = lessonService;
    }


//    @Transactional
//    @Scheduled(fixedRate = 1800000)
    public void scanFile() {
        String[] files = {"TKB_Manh","TKB_Hung","TKB_Hoi","TKB_Hoang","TKB_Du"};
        Arrays.stream(files).forEach(this::readData);
    }





    public void readData(String fileName) {

        log.info(":: Start");
        String path = "src\\main\\resources\\files\\";
        ReadExcelFile readData = new ReadExcelFile(path + fileName + ".xls");


        List<ScheduleExcelDTO> schedules = readData.schedules;
        StudentExcelDTO student = readData.student;
        StudentCreateDTO studentCreateDTO = new StudentCreateDTO(student.getStudentCode(), student.getStudentName(), student.getMajor(), student.getClassName(), true);

        try {

            studentService.create(studentCreateDTO);

            student.getSchedules().forEach(x -> {
                ScheduleCreateDTO scheduleCreateDTO = new ScheduleCreateDTO(studentCreateDTO.getCode(), x.getDate(), true);
                log.info("scheduleCreateDTO");
                try {
                    Integer scheduleID = scheduleService.create(scheduleCreateDTO);
                    log.info("scheduleID");
                    if (scheduleID != null) {
                        x.getLessons().forEach(l -> {
                            LessonCreateDTO lessonCreateDTO = new LessonCreateDTO(l.getLesson(), l.getSubjectName(), l.getAddress(), scheduleID, true);
                            try {
                                lessonService.create(lessonCreateDTO);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(":: Get Data Excel SUCCESS from Student:" + studentCreateDTO.getName().toUpperCase() + "\tMSV: " + studentCreateDTO.getCode());

    }
}

