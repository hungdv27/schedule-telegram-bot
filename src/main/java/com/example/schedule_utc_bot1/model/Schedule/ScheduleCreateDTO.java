package com.example.schedule_utc_bot1.model.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleCreateDTO {
    private String studentCode;
    private Date date;
    private boolean isActive;
}
