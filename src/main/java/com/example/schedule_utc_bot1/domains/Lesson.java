package com.example.schedule_utc_bot1.domains;

import com.example.schedule_utc_bot1.model.Lesson.LessonCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(columnDefinition = "nvarchar(255)")
    private String lessonNum;

    @Column(columnDefinition = "nvarchar(255)")
    private String subjectName;

    @Column(columnDefinition = "nvarchar(255)")
    private String address;

    @Column
    private boolean isActive;

    @Column
    private Integer scheduleID;

    public Lesson(LessonCreateDTO dto) {
        this.lessonNum = dto.getLessonNum();
        this.subjectName = dto.getSubjectName();
        this.address = dto.getAddress();
        this.scheduleID = dto.getScheduleID();
        this.isActive = dto.isActive();
    }
}

