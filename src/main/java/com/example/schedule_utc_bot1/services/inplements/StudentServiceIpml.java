package com.example.schedule_utc_bot1.services.inplements;


import com.example.schedule_utc_bot1.domains.Student;
import com.example.schedule_utc_bot1.model.Student.StudentCreateDTO;
import com.example.schedule_utc_bot1.model.Student.StudentDTO;
import com.example.schedule_utc_bot1.repositories.AccountRepository;
import com.example.schedule_utc_bot1.repositories.StudentRepository;
import com.example.schedule_utc_bot1.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentServiceIpml implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public StudentDTO findByCode(String code) {
        Optional<Student> studentOptional = repository.findTop1ByCodeOrderByCode(code);
        if(studentOptional.isEmpty()){
            log.info( String.format("StudentCode %s không tồn tại [find student by code error]", code));
        }
        Student student = studentOptional.get();
        return new StudentDTO(student);
    }

    @Override
    public Integer create(StudentCreateDTO dto) throws Exception {
        if(repository.existsByCode(dto.getCode())){
            throw new Exception(String.format("StudentCode %s đã tồn tại [create student error]", dto.getCode()));
        }
        Student student = new Student(dto);
         return repository.save(student).getId();
    }

    @Override
    public boolean update(StudentCreateDTO dto) {
        if(!repository.existsByCode(dto.getCode()))  return false;
        Student student = new Student(dto);
        repository.save(student);
        return true;
    }

    @Override
    public void delete(String code) throws Exception {
        if(!repository.existsByCode(code)){
            throw new Exception(String.format("StudentCode %s đã tồn tại [delete student error]", code));
        }


        // cần xoá nhiều schedule, account trước sau đó xoá student

        accountRepository.deleteAccountByStudentCode(code);
        repository.deleteByCode(code);
    }

    @Override
    public List<StudentDTO> findAll()  {
        List<Student> students = repository.findAll();
        if(CollectionUtils.isEmpty(students)){
            log.error("Không tồn tại dữ liệu trong bảng Student [Student find all error]");
            return null;
        }
        List<StudentDTO> studentDTOS = new ArrayList<>();
        students.forEach(x-> {studentDTOS.add(new StudentDTO(x));});
        return studentDTOS;
    }

    @Override
    public Integer getIdByCode(String code) throws Exception {
        Optional<Student> optional = repository.findTop1ByCodeOrderByCode(code);
        if(optional.isEmpty()){
            throw new RuntimeException(String.format("StudentCode %s không tồn tại [get Id By Code error]", code) );
        }
        return optional.get().getId();
    }
}
