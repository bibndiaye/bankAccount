package com.mndiaye.bank.bankaccount.repo;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.OperationType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PopulateBank {

    public List<BankAccount> createbankAccounts ( List<Operation> operations ){
        BankAccount bankAccount;
        List<BankAccount> bankAccounts = new ArrayList<>();
        for (int i=0; i<3; i++){
            bankAccount = new BankAccount(i, 0, operations);
            bankAccounts.add(bankAccount);
        }

        return bankAccounts;
    }
    public List<Operation> operations (){
        Operation operation1 = new Operation(1, LocalDateTime.now(),400, OperationType.DEPOSIT);
        Operation operation2 = new Operation(2, LocalDateTime.now(),500, OperationType.DEPOSIT);
        Operation operation3 = new Operation(3, LocalDateTime.now(),150, OperationType.WITHDRAWAL);

        Operation operation4 = new Operation(4, LocalDateTime.now(),170, OperationType.DEPOSIT);
        Operation operation5 = new Operation(5, LocalDateTime.now(),100, OperationType.WITHDRAWAL);
        Operation operation6 = new Operation(6, LocalDateTime.now(),300, OperationType.DEPOSIT);

        Operation operation7 = new Operation(7, LocalDateTime.now(),900, OperationType.DEPOSIT);
        Operation operation8 = new Operation(8, LocalDateTime.now(),300, OperationType.DEPOSIT);
        Operation operation9 = new Operation(9, LocalDateTime.now(),200, OperationType.WITHDRAWAL);

        List<Operation> operations = new ArrayList<>();
        operations.add(operation1);
        operations.add(operation2);
        operations.add(operation3);

        operations.add(operation4);
        operations.add(operation5);
        operations.add(operation6);

        operations.add(operation7);
        operations.add(operation8);
        operations.add(operation9);

        return operations;
    }

    public  Operation createOperation (Operation operation){

        return null;
    }
    public void addBanKAccount (){

        Operation operation1 = new Operation(1, LocalDateTime.now(),400, OperationType.DEPOSIT);
        Operation operation2 = new Operation(2, LocalDateTime.now(),500, OperationType.DEPOSIT);
        Operation operation3 = new Operation(3, LocalDateTime.now(),150, OperationType.WITHDRAWAL);

        Operation operation4 = new Operation(4, LocalDateTime.now(),170, OperationType.DEPOSIT);
        Operation operation5 = new Operation(5, LocalDateTime.now(),100, OperationType.WITHDRAWAL);
        Operation operation6 = new Operation(6, LocalDateTime.now(),300, OperationType.DEPOSIT);

        Operation operation7 = new Operation(7, LocalDateTime.now(),900, OperationType.DEPOSIT);
        Operation operation8 = new Operation(8, LocalDateTime.now(),300, OperationType.DEPOSIT);
        Operation operation9 = new Operation(9, LocalDateTime.now(),200, OperationType.WITHDRAWAL);

        List<Operation> operations1 = new ArrayList<>();
        List<Operation> operations2 = new ArrayList<>();
        List<Operation> operations3 = new ArrayList<>();
        operations1.add(operation1);
        operations1.add(operation2);
        operations1.add(operation3);

        operations2.add(operation4);
        operations2.add(operation5);
        operations2.add(operation6);

        operations3.add(operation7);
        operations3.add(operation8);
        operations3.add(operation9);


        BankAccount bankAccount1 = new BankAccount(1, 0, operations1);
        BankAccount bankAccount2 = new BankAccount(2, 0, operations2);
        BankAccount bankAccount3 = new BankAccount(3, 0, operations3);

    }

}
