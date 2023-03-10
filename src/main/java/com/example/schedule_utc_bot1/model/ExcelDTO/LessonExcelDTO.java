package com.example.schedule_utc_bot1.model.ExcelDTO;

public class LessonExcelDTO {
    private int id;
    private String lesson;
    private String subjectName;
    private String address;

    public LessonExcelDTO(String lesson, String subjectName, String address) {
        this.lesson = lesson;
        this.subjectName = subjectName;
        this.address = address;
    }

    public LessonExcelDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lesson='" + lesson + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
