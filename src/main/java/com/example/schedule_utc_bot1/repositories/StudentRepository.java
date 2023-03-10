package com.example.schedule_utc_bot1.repositories;


import com.example.schedule_utc_bot1.domains.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findTop1ByCodeOrderByCode(String code);

    boolean existsByCode(String code);

    void deleteByCode(String codde);

    @Override
    List<Student> findAll();
}
