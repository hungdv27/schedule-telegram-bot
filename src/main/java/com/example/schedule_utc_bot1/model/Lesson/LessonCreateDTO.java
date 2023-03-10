package com.example.schedule_utc_bot1.model.Lesson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonCreateDTO {
    private String lessonNum;
    private String subjectName;
    private String address;
    private Integer scheduleID;
    private boolean isActive;
}
