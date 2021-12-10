package com.mndiaye.bank.bankaccount.controller;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.domaine.dto.OperationCommand;
import com.mndiaye.bank.bankaccount.enums.OperationType;
import com.mndiaye.bank.bankaccount.mapper.AccountDtoMapper;
import com.mndiaye.bank.bankaccount.service.BankAccountService;
import com.mndiaye.bank.bankaccount.service.OperationService;
import com.mndiaye.bank.bankaccount.exception.NoSuchAccountException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@SpringBootTest(classes = BankAccountControllerTest.class)
@Slf4j
public class BankAccountControllerTest {

    @InjectMocks
    OperationService operationService;

    @InjectMocks
    AccountDtoMapper mapper;


    @InjectMocks
    AccountDto accountDto;

    List<Operation> operations ;
    @BeforeEach
    public void initOperations() {
         accountDto  = new AccountDto();
         operations = operationService.operations();
    }

    /***
     *
     * @throws NoSuchAccountException
     */
    @Test
    public void showOperationsListTest(){
        operations = operationService.listAllOperations(1);
        Assertions.assertNotNull(operations);
        Assertions.assertEquals(3, operations.size());
    }

    /***
     *
     * @throws NoSuchAccountException
     */
    @Test
    public void depositTest()  {

        OperationCommand operationCommand = new OperationCommand(200);
        Operation operation = operationService.createAndPerformOperation(1,operationCommand.getAmount(), OperationType.DEPOSIT);
        BankAccount bankAccount = operationService.createbankAccounts(operations).get(0);
        bankAccount.getOperations().add(operation);
        accountDto = mapper.mapEntityToDto(bankAccount);
        Assertions.assertNotNull(accountDto);
        Assertions.assertEquals(4, accountDto.getLatestOperations().size());
        //Assertions.assertEquals(-400, accountDto.getLatestOperations().get(0).getAmount());
        Assertions.assertEquals(-50, accountDto.getBalance());

    }
    @Test
    public void withdrawalTest()  {

        OperationCommand operationCommand = new OperationCommand(50);
        log.info("operationCommand = "+ operationCommand.getAmount());

        Operation operation = operationService.createAndPerformOperation(1,operationCommand.getAmount(), OperationType.WITHDRAWAL);
        BankAccount account = operationService.createbankAccounts(operations).get(0);
        account.getOperations().add(operation);
        accountDto.setBalance(operationService.getBanceFromAllOp(operations));
        accountDto.setLatestOperations(operations);
        Assertions.assertNotNull(accountDto);
        Assertions.assertEquals(4, operations.size());
        Assertions.assertEquals(-100, accountDto.getBalance());

    }


}
