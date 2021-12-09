package com.mndiaye.bank.bankaccount.service;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.enums.OperationType;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.mapper.AccountDtoMapper;
import com.mndiaye.bank.bankaccount.exception.NoSuchAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OperationService {



    @Autowired
    private AccountDtoMapper dtoMapper;

    @Autowired
    BankAccountService bankAccountService;

    public static final int SIZE_OPERATIONS_INIT = 3;
    /**
     * debits the specified amount on the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public AccountDto doWithdrawal(int accountId, long amount) throws NoSuchAccountException {

        OperationType operationWithDrawal = OperationType.WITHDRAWAL;

        Operation operation = createAndPerformOperation(accountId,amount, operationWithDrawal);
        BankAccount bankAccount = createbankAccounts(operations()).get(accountId);

        bankAccount.getOperations().add(operation);
        AccountDto accountDto = new AccountDto();

        long totalAmount= bankAccountService.getTotalAmountByOperationType(bankAccount, operationWithDrawal);


        accountDto.setBalance(Math.abs(totalAmount));
        bankAccount.balance=totalAmount;
        accountDto.setLatestOperations(bankAccount.getOperations());
        bankAccount.getOperations().add(operation);
        return dtoMapper.mapEntityToDto(bankAccount);
    }

    /**
     * deposit the specified amount into the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public AccountDto doDeposit(int accountId, long amount)  {

        OperationType operationWithDeposit = OperationType.DEPOSIT;
        Operation operation = createAndPerformOperation(accountId,amount,operationWithDeposit);
        BankAccount bankAccount = createbankAccounts(operations()).get(accountId);
        bankAccount.getOperations().add(operation);
        AccountDto accountDto = new AccountDto();

        long totalAmount= bankAccountService.getTotalAmountByOperationType(bankAccount, operationWithDeposit);

        accountDto.setBalance(totalAmount);
        bankAccount.balance=totalAmount;
        accountDto.setLatestOperations(bankAccount.getOperations());

        return dtoMapper.mapEntityToDto(bankAccount);
    }

    /**
     * create and perform the specified operation on the given account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @param operationType the transaction type(debit or credit)
     * @return newly created operation
     * @throws NoSuchAccountException
     */

    public  Operation createAndPerformOperation(int accountId, long amount, OperationType operationType) {
        BankAccount account = createbankAccounts(operations()).get(accountId);

        int opType = operationType.equals(OperationType.WITHDRAWAL) ? -1 : 1;
        Operation operation = new Operation();
        operation.setAmount(opType*amount);
        operation.setDate(LocalDateTime.now());
        operation.setType(operationType);
        account.balance+=opType*amount;
        operations().add(operation);
        account.setOperations(operations());
        AccountDto dto = new AccountDto();
        dto.setBalance(account.balance);
        dto.setLatestOperations(operations());

        return operation;
    }
    /**
     *
     * @param accountId account identifier
     * @return all operations on a given account
     * @throws NoSuchAccountException
     */
    public List<Operation> listAllOperations(int accountId) {
        return createbankAccounts(operations()).get(accountId).getOperations();
    }

    /**
     *
     * @param operations
     * @return all operation done per account
     */

    public List<BankAccount> createbankAccounts (List<Operation> operations ){
        BankAccount bankAccount;
        List<BankAccount> bankAccounts = new ArrayList<>();
        long balance= getBanceFromAllOp(operations);

        for (int i=1; i< SIZE_OPERATIONS_INIT; i++){
            bankAccount = new BankAccount(i, balance, operations);
            bankAccounts.add(bankAccount);
        }

        return bankAccounts;
    }

    /**
     *
     * @return all operations done per account
     */
    public List<Operation> operations (){

        // init three first operations
        Operation operation1 = new Operation(1, LocalDateTime.now(),-400, OperationType.WITHDRAWAL);
        Operation operation2 = new Operation(2, LocalDateTime.now(),500, OperationType.DEPOSIT);
        Operation operation3 = new Operation(3, LocalDateTime.now(),-150, OperationType.WITHDRAWAL);


        List<Operation> operations = new ArrayList<>();

        operations.add(operation1);
        operations.add(operation2);
        operations.add(operation3);


        return operations;
    }

    /**
     *
     * @param operations
     * @return balance of a bank accountid
     */
    public long getBanceFromAllOp(List<Operation> operations) {
        int balance=0;
        if ((operations==null)) return  0;

        for (Operation operation: operations){
            balance+=operation.getAmount();
        }
        return balance;
    }



}
