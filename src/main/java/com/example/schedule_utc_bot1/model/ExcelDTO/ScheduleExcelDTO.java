package com.example.schedule_utc_bot1.model.ExcelDTO;



import org.apache.commons.collections4.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScheduleExcelDTO {
    private int id;
    private String dateString;
    private Date date;
    private List<LessonExcelDTO> lessons;


    public ScheduleExcelDTO(Date date, List<LessonExcelDTO> lessons) {
        this.date = date;
        this.lessons = lessons;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.dateString = dateFormat.format(date);
    }

    public ScheduleExcelDTO() {

    }

    public void addOneLesson(LessonExcelDTO addLesson) {
        if (CollectionUtils.isEmpty(lessons)) {
            return;
        }
        this.lessons.add(addLesson);
    }

    public List<LessonExcelDTO> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonExcelDTO> lessons) {
        this.lessons = lessons;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.dateString = dateFormat.format(date);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", dateString='" + dateString + '\'' +
                ", date=" + date +
                ", lessons=" + lessons +
                '}';
    }
}
