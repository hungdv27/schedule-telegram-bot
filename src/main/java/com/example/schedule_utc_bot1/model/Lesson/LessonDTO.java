package com.example.schedule_utc_bot1.model.Lesson;


import com.example.schedule_utc_bot1.domains.Lesson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private String lessonNum;
    private String subjectName;
    private String address;
    private boolean isActive;
    private Integer scheduleID;

    public LessonDTO(Lesson lesson){
        this.lessonNum = lesson.getLessonNum();
        this.subjectName = lesson.getSubjectName();
        this.address = lesson.getAddress();
        this.scheduleID = lesson.getScheduleID();
        this.isActive = lesson.isActive();
    }

    @Override
    public String toString() {
        return "LessonDTO{" +
                "lessonNum='" + lessonNum + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", scheduleID=" + scheduleID +
                '}';
    }
}
