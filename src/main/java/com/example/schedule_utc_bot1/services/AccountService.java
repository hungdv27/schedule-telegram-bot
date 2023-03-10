package com.example.schedule_utc_bot1.services;


import com.example.schedule_utc_bot1.model.Account.AccountCreateDTO;
import com.example.schedule_utc_bot1.model.Account.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    AccountDTO findById(Integer id) throws Exception;

    Integer create(AccountCreateDTO dto) throws Exception;

    boolean update(AccountDTO dto) throws Exception;

    void delete(Integer id) throws Exception;

    List<AccountDTO> findAll();

    Long findAccountCodeByStudentCode(String studentCode);
}
