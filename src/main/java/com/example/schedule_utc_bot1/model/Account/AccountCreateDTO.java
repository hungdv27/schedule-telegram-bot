package com.example.schedule_utc_bot1.model.Account;


import com.example.schedule_utc_bot1.domains.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountCreateDTO {
    private Integer id;
    private String studentCode;
    private String name;
    private Long code;

    public AccountCreateDTO(Account account){
        this.studentCode = account.getStudentCode();
        this.name =  account.getName();
        this.code = account.getCode();
    }

}
