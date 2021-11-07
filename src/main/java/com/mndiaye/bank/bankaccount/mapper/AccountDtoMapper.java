package com.mndiaye.bank.bankaccount.mapper;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountDtoMapper {

    public AccountDto mapEntityToDto(BankAccount account){
        AccountDto dto = new AccountDto();
        dto.setBalance(account.balance);
        List<Operation> recentOps = account.getOperations()
                .stream()
                .sorted(Comparator.comparing(Operation::getDate).reversed())
                .limit(4).collect(Collectors.toList());
        dto.setLatestOperations(recentOps);
        return dto;
    }
}
