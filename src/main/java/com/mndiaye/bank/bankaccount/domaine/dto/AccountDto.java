package com.mndiaye.bank.bankaccount.domaine.dto;


import com.mndiaye.bank.bankaccount.domaine.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private long balance;

    private List<Operation> latestOperations;
}
