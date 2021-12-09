package com.mndiaye.bank.bankaccount.mapper;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.service.BankAccountService;
import com.mndiaye.bank.bankaccount.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class AccountDtoMapperTest {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    OperationService operationService;

    @Autowired
    AccountDtoMapper accountDtoMapper;

    List<Operation> operations ;

    @BeforeEach
    public void initOperations() {
        operations = operationService.operations();
    }

    @Test
    public void mapEntityToDtoTest(){

        BankAccount bankAccount = operationService.createbankAccounts(operations).get(0);

        AccountDto accountDto = accountDtoMapper.mapEntityToDto(bankAccount);
        Assertions.assertNotNull(accountDto);
        Assertions.assertEquals(3, accountDto.getLatestOperations().size());


    }
}
