package com.example.schedule_utc_bot1.repositories;


import com.example.schedule_utc_bot1.domains.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsAccountByStudentCode(String studentCode);

    Optional<Account> findByStudentCode(String studentCode);

    Optional<Account> findById(Integer id);

    void deleteAccountByStudentCode(String studentCode);

    Account findTopByStudentCode(String studentCode);

}
