package com.example.schedule_utc_bot1.model.Student;

import com.example.schedule_utc_bot1.domains.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentCreateDTO {
    private String code;
    private String name;
    private String major;
    private String className;
    private boolean isActive;

    public StudentCreateDTO(Student student) {
        this.code = student.getCode();
        this.name = student.getName();
        this.major = student.getMajor();
        this.className = student.getClassName();
        this.isActive = student.isActive();
    }
}
