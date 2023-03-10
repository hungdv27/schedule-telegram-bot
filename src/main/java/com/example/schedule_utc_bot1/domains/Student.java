package com.example.schedule_utc_bot1.domains;

import com.example.schedule_utc_bot1.model.Student.StudentCreateDTO;
import com.example.schedule_utc_bot1.model.Student.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(columnDefinition = "nvarchar(255)")
    private String code;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Column(columnDefinition = "nvarchar(255)")
    private String className;

    @Column(columnDefinition = "nvarchar(255)")
    private String major;

    @Column
    private boolean isActive;

    public Student(StudentCreateDTO dto) {
        this.code = dto.getCode();
        this.name = dto.getName();
        this.className = dto.getClassName();
        this.major = dto.getMajor();
        this.isActive = dto.isActive();
    }

    public Student(StudentDTO dto) {
        this.code = dto.getCode();
        this.name = dto.getName();
        this.className = dto.getClassName();
        this.major = dto.getMajor();
        this.isActive = dto.isActive();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", major='" + major + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
