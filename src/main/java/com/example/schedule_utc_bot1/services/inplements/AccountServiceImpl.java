package com.example.schedule_utc_bot1.services.inplements;


import com.example.schedule_utc_bot1.domains.Account;
import com.example.schedule_utc_bot1.model.Account.AccountCreateDTO;
import com.example.schedule_utc_bot1.model.Account.AccountDTO;
import com.example.schedule_utc_bot1.repositories.AccountRepository;
import com.example.schedule_utc_bot1.services.AccountService;
import com.example.schedule_utc_bot1.services.StudentService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private StudentService studentService;


    @Override
    public AccountDTO findById(Integer id) throws Exception {
        Optional<Account> account = repository.findById(id);
        if (account.isPresent()) {
            return new AccountDTO(account.get());
        }
        return null;
    }

    @Override
    public Integer create(AccountCreateDTO dto) throws Exception {
        if (repository.existsAccountByStudentCode(dto.getStudentCode())) return null;
        Account account = new Account(dto);
        return repository.save(account).getId();
    }


    @Override
    public boolean update(AccountDTO dto) throws Exception {
        Optional<Account> optional = repository.findById(dto.getId());
        // xét xem ID có tồn tại trong data chưa
        if (optional.isPresent()) {
            Account accountDB = optional.get();
            // xét xem StudentCode dto mới có trùng với studentCode(khác ID) có trong data ko
            // nếu tồn tại dto.getStudentCode trong data mà không trùng với ID Acc hiện tại thì trả về false
            if (repository.existsAccountByStudentCode(dto.getStudentCode()) && !accountDB.getStudentCode().equals(dto.getStudentCode())) {
                return false;
            }
            Account accountNew = new Account(dto);
            repository.save(accountNew);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Integer id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public List<AccountDTO> findAll() {
        List<Account> all = repository.findAll();
        if (CollectionUtils.isNotEmpty(all)) {
            List<AccountDTO> accountDTOS = new ArrayList<>();
            all.forEach(x -> {
                accountDTOS.add(new AccountDTO(x));
            });
            return accountDTOS;
        }
        return null;
    }

    @Override
    public Long findAccountCodeByStudentCode(String studentCode) {
        Account acc = repository.findTopByStudentCode(studentCode.trim());
        if(acc != null && acc.getCode() != null ){
            return acc.getCode();
        }
        return null;
    }
}
