package com.mndiaye.bank.bankaccount.service;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.enums.OperationType;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.mapper.AccountDtoMapper;
import com.mndiaye.bank.bankaccount.utils.NoSuchAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class OperationService {



    @Autowired
    private AccountDtoMapper dtoMapper;
    /**
     * debits the specified amount on the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchAccountException
     */
    public AccountDto doWithdrawal(int accountId, long amount) throws NoSuchAccountException {
            Operation operation = createAndPerformOperation(accountId,amount, OperationType.WITHDRAWAL);
        BankAccount bankAccount = createbankAccounts(operations()).get(accountId);

        bankAccount.getOperations().add(operation);
        AccountDto accountDto = new AccountDto();
        long sum=0;
        for (int i = 0; i <bankAccount.getOperations().size() ; i++) {
            log.info("typeOp = "+bankAccount.getOperations().get(i).getType());
            if (bankAccount.getOperations().get(i).getType().equals(OperationType.WITHDRAWAL)){
                sum-=Math.abs(bankAccount.getOperations().get(i).getAmount());
            }else {
                sum+=bankAccount.getOperations().get(i).getAmount();
            }
        }
        log.info("sum= "+sum);
        accountDto.setBalance(Math.abs(sum));
        bankAccount.balance=sum;
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
    public AccountDto doDeposit(int accountId, long amount) throws NoSuchAccountException {
        Operation operation = createAndPerformOperation(accountId,amount,OperationType.DEPOSIT);
        BankAccount bankAccount = createbankAccounts(operations()).get(accountId);
        bankAccount.getOperations().add(operation);
        AccountDto accountDto = new AccountDto();
        long sum=0;
        for (int i = 0; i <bankAccount.getOperations().size() ; i++) {
            log.info("typeOp = "+bankAccount.getOperations().get(i).getType());
            if (bankAccount.getOperations().get(i).getType().equals(OperationType.WITHDRAWAL)){
                sum-=Math.abs(bankAccount.getOperations().get(i).getAmount());
            }else {
                sum+=bankAccount.getOperations().get(i).getAmount();
            }
        }
        log.info("sum= "+sum);
        accountDto.setBalance(sum);
        bankAccount.balance=sum;
        accountDto.setLatestOperations(bankAccount.getOperations());
        log.info("bankAccount op size = "+bankAccount.getOperations().size());
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

    public  Operation createAndPerformOperation(int accountId, long amount, OperationType operationType) throws NoSuchAccountException {
        BankAccount account = createbankAccounts(operations()).get(accountId);

        int opType = operationType.equals(OperationType.WITHDRAWAL) ? -1 : 1;
        Operation operation = new Operation();
        operation.setAmount(opType*amount);
        operation.setDate(LocalDateTime.now());
        operation.setType(operationType);
        log.info("account.getBalance()+opType*amount = {}",account.getBalance()+opType*amount);
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
        BankAccount account = createbankAccounts(operations()).get(accountId);

        return account.getOperations();
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
        for (int i=1; i<3; i++){
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
        int cpt=0;
        if ((operations==null)) return  0;

        for (Operation op: operations){
            log.info("montant = "+op.getAmount());
            cpt+=op.getAmount();
        }
        log.info("cpt = "+cpt);
        return cpt;
    }
}
