package com.example.schedule_utc_bot1.domains;

import com.example.schedule_utc_bot1.model.Schedule.ScheduleCreateDTO;
import com.example.schedule_utc_bot1.model.Schedule.ScheduleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(columnDefinition = "nvarchar(255)")
    private String studentCode;

    @Column
    private Date date;

    @Column
    private boolean isActive;


    public Schedule(ScheduleCreateDTO dto) {
        this.studentCode = dto.getStudentCode();
        this.date = dto.getDate();
        this.isActive = dto.isActive();
    }

    public Schedule(ScheduleDTO dto) {
        this.id = dto.getId();
        this.studentCode = dto.getStudentCode();
        this.date = dto.getDate();
        this.isActive = dto.isActive();
    }
}
