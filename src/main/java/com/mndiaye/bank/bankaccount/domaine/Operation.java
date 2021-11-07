package com.mndiaye.bank.bankaccount.domaine;

import com.mndiaye.bank.bankaccount.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private int id;
    private LocalDateTime date;
    private long amount ;
    private OperationType type;

}
