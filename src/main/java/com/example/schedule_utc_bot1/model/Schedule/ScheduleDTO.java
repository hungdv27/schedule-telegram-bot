package com.example.schedule_utc_bot1.model.Schedule;


import com.example.schedule_utc_bot1.domains.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private Integer id;
    private String studentCode;
    private Date date;
    private boolean isActive;

    public ScheduleDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.studentCode = schedule.getStudentCode();
        this.date = schedule.getDate();
        this.isActive = schedule.isActive();
    }
}
