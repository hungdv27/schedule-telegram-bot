package com.example.schedule_utc_bot1.model.ExcelDTO;

import java.util.List;

public class StudentExcelDTO {
    private int id;
    private String studentCode;
    private String studentName;
    private String major;
    private String className;
    private List<ScheduleExcelDTO> schedules;

    public StudentExcelDTO(String studentCode, String studentName, String major, String className, List<ScheduleExcelDTO> schedules) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.major = major;
        this.className = className;
        this.schedules = schedules;
    }

    public StudentExcelDTO(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ScheduleExcelDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleExcelDTO> schedules) {
        this.schedules = schedules;
    }
}
