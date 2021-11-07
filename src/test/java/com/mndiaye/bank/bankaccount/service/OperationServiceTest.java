package com.mndiaye.bank.bankaccount.service;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.enums.OperationType;
import com.mndiaye.bank.bankaccount.utils.NoSuchAccountException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest(classes = OperationServiceTest.class)
@Slf4j
public class OperationServiceTest {

    @InjectMocks
    OperationService operationService;

    /**
     * Test listOpertions
     */
    @Test
    public void listAllOperationsTest() {
        List<Operation> operations = operationService.operations();
        BankAccount account = operationService.createbankAccounts(operations).get(0);
        Assertions.assertNotNull( operations);
        Assertions.assertNotNull(account);
        Assertions.assertEquals(3, operations.size());
        Assertions.assertEquals(-50, account.getBalance());
    }

    /***
     * Test deposit money in an account bank
     * @throws NoSuchAccountException
     */
    @Test
    public void doDeposit() throws NoSuchAccountException {
        Operation operation = operationService.createAndPerformOperation(1,200, OperationType.DEPOSIT);
        List<Operation> operations = operationService.operations();
        BankAccount account = operationService.createbankAccounts(operations).get(0);
        Assertions.assertNotNull(operations);
        Assertions.assertNotNull(account);
        Assertions.assertEquals(3, operations.size());
        Assertions.assertEquals( -50, account.getBalance());
        log.info("deposit : $200 bank transfert ");
        account.getOperations().add(operation);
        Assertions.assertEquals(4, operations.size());
        account.balance = operationService.getBanceFromAllOp(operations);
        Assertions.assertEquals( 150, account.getBalance());


    }

    /**
     * Test drawal money in an account bank
     * @throws NoSuchAccountException
     */
    @Test
    public void doWithdrawal() throws NoSuchAccountException {
        Operation operation = operationService.createAndPerformOperation(1,70, OperationType.WITHDRAWAL);
        List<Operation> operations = operationService.operations();
        BankAccount account = operationService.createbankAccounts(operations).get(0);
        Assertions.assertNotNull(operations);
        Assertions.assertNotNull(account);
        Assertions.assertEquals(3, operations.size());
        Assertions.assertEquals( -50, account.getBalance());
        log.info("drawal : $150 ");
        account.getOperations().add(operation);
        Assertions.assertEquals(4, operations.size());
        account.balance = operationService.getBanceFromAllOp(operations);
        Assertions.assertEquals( -120, account.getBalance());

    }

}
