package com.mndiaye.bank.bankaccount.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    private int id;
    public long balance;
    private List<Operation> operations = new ArrayList<>();

}
