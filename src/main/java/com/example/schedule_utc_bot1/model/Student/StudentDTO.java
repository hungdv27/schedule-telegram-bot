package com.example.schedule_utc_bot1.model.Student;


import com.example.schedule_utc_bot1.domains.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String code;
    private String name;
    private String major;
    private String className;
    private boolean isActive;

    public StudentDTO(Student student) {
        this.code = student.getCode();
        this.name = student.getName();
        this.major = student.getMajor();
        this.className = student.getClassName();
        this.isActive = student.isActive();
    }

    @Override
    public String toString() {
        return "StudentDTO{" + "code='" + code + '\'' + ", name='" + name + '\'' + ", major='" + major + '\'' + ", className='" + className + '\'' + ", isActive=" + isActive + '}';
    }
}
