package com.mndiaye.bank.bankaccount.service;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.enums.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class BankAccountServiceTest {

    @Autowired
    OperationService operationService;

    @Autowired
    BankAccountService bankAccountService;

    List<Operation> operations ;

    @BeforeEach
    public void initOperations() {
        operations = operationService.operations();
    }
    @Test
    public void getTotalAmountByOperationTypeTest(){

        BankAccount bankAccount = operationService.createbankAccounts(operations).get(0);
        Assertions.assertNotNull(bankAccount);
        long totalAmount = bankAccountService.getTotalAmountByOperationType(bankAccount, OperationType.WITHDRAWAL);
        Assertions.assertEquals(-50, totalAmount);
        log.info("totalAmount = "+totalAmount);
    }

    @Test
    @DisplayName("Should fail if bankAccount is null")
    public void getTotalAmountByOperationTypeTestError(){

        BankAccount bankAccount = operationService.createbankAccounts(operations).get(0);
        Assertions.assertNotNull(bankAccount);
        long totalAmount = bankAccountService.getTotalAmountByOperationType(bankAccount, OperationType.WITHDRAWAL);
        Assertions.assertNotEquals(200, totalAmount);
    }
}
