package com.mndiaye.bank.bankaccount.controller;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.domaine.dto.OperationCommand;
import com.mndiaye.bank.bankaccount.enums.OperationType;
import com.mndiaye.bank.bankaccount.mapper.AccountDtoMapper;
import com.mndiaye.bank.bankaccount.service.OperationService;
import com.mndiaye.bank.bankaccount.utils.NoSuchAccountException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@SpringBootTest(classes = BankAccountControllerTest.class)
@Slf4j
public class BankAccountControllerTest {
    @InjectMocks
    OperationService operationService;

    @Mock
    AccountDtoMapper mapper;


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
    public void showOperationsListTest() throws NoSuchAccountException {
        operations = operationService.listAllOperations(1);
        Assertions.assertNotNull(operations);
        Assertions.assertEquals(3, operations.size());
    }

    /***
     *
     * @throws NoSuchAccountException
     */
    @Test
    public void depositTest() throws NoSuchAccountException {

        OperationCommand operationCommand = new OperationCommand(200);
        Operation operation = operationService.createAndPerformOperation(1,operationCommand.getAmount(), OperationType.DEPOSIT);
        BankAccount account = operationService.createbankAccounts(operations).get(0);
        account.getOperations().add(operation);
        accountDto.setBalance(operationService.getBanceFromAllOp(operations));
        accountDto.setLatestOperations(operations);
        Assertions.assertNotNull(accountDto);
        Assertions.assertEquals(4, operations.size());
        Assertions.assertEquals(150, accountDto.getBalance());

    }
    @Test
    public void withdrawalTest() throws NoSuchAccountException {

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
