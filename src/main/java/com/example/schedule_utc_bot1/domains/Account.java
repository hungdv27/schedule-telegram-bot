package com.example.schedule_utc_bot1.domains;


import com.example.schedule_utc_bot1.model.Account.AccountCreateDTO;
import com.example.schedule_utc_bot1.model.Account.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private String studentCode;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    @Column
    private Long code;

    public Account(AccountCreateDTO dto) {
        this.studentCode = dto.getStudentCode();
        this.name = dto.getName();
        this.code = dto.getCode();
    }

    public Account(AccountDTO dto){
        this.id = dto.getId();
        this.studentCode = dto.getStudentCode();
        this.name = dto.getName();
        this.code = dto.getCode();
    }
}