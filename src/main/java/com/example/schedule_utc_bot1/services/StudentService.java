package com.example.schedule_utc_bot1.services;

import com.example.schedule_utc_bot1.model.Student.StudentCreateDTO;
import com.example.schedule_utc_bot1.model.Student.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    StudentDTO findByCode(String code) ;

    Integer create(StudentCreateDTO dto) throws Exception;

    boolean update(StudentCreateDTO dto);

    void delete(String code) throws Exception;

    List<StudentDTO> findAll() ;

    Integer getIdByCode(String code) throws Exception;
}
