package com.mndiaye.bank.bankaccount.service;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.enums.OperationType;
import com.mndiaye.bank.bankaccount.exception.NoSuchAccountException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = OperationServiceTest.class)
@Slf4j
public class OperationServiceTest {

    @InjectMocks
    OperationService operationService;


    List<Operation> operations ;
    AccountDto accountDto;

    @BeforeEach
    public void initOperations() {
        operations = operationService.operations();
         accountDto = new AccountDto();
    }

    /**
     * Test listOpertions
     */
    @Test
    public void listAllOperationsTest() {
        
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
    public void doDepositTest() throws NoSuchAccountException {
        Operation operation = operationService.createAndPerformOperation(1,200, OperationType.DEPOSIT);
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
    public void doWithdrawalTest() throws NoSuchAccountException {
        Operation operation = operationService.createAndPerformOperation(1,70, OperationType.WITHDRAWAL);
        //BankAccount account = operationService.createbankAccounts(operations).get(0);
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
    @Test
    public  void createAndPerformOperationTest(){
        int opTypeDeposit = 1;
        long amountDeposit = 120;

        BankAccount account = operationService.createbankAccounts(operations).get(0);

        Assertions.assertNotNull(account);
        Assertions.assertEquals(account.getOperations().size(), 3);
        Assertions.assertEquals(account.getBalance(), -50);
        Assertions.assertEquals(account.getOperations().get(0).getAmount(), -400);

        Operation operationDeposit = new Operation();
        operationDeposit.setAmount(opTypeDeposit*amountDeposit);
        operationDeposit.setDate(LocalDateTime.now());
        operationDeposit.setType(OperationType.DEPOSIT);
        account.balance+=opTypeDeposit*amountDeposit;
        operations.add(operationDeposit);
        account.setOperations(operations);

        Assertions.assertEquals(account.getOperations().size(), 4);

        accountDto.setBalance(account.balance);
        accountDto.setLatestOperations(operations);

        Assertions.assertNotNull(accountDto);

        Assertions.assertEquals(accountDto.getLatestOperations().size(), 4);
        Assertions.assertEquals(accountDto.getBalance(), 70);


    }


    @Test
    public void getBanceFromAllOpTest() {

        Assertions.assertNotNull(operations);
        long balance = operations.stream().mapToLong(amount->amount.getAmount()).sum();

        Assertions.assertEquals(balance, -50);

    }

    @Test
    public void operationsTest(){
        Assertions.assertNotNull(operations);
        Assertions.assertEquals(operations.size(), 3);
        Assertions.assertEquals(operations.get(0).getAmount(), -400);
        Assertions.assertEquals(operations.get(0).getType(), OperationType.WITHDRAWAL);
        Assertions.assertEquals(operations.get(1).getAmount(), 500);
        Assertions.assertEquals(operations.get(1).getType(), OperationType.DEPOSIT);
        Assertions.assertEquals(operations.get(2).getAmount(), -150);
        Assertions.assertEquals(operations.get(2).getType(), OperationType.WITHDRAWAL);
    }

    @Test
    public void NoSuchAccountExceptionTest(){
        NoSuchAccountException noSuchAccountException = new NoSuchAccountException("Error. No such account found");
        Assertions.assertNotNull(noSuchAccountException);
    }
}
