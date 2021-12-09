package com.mndiaye.bank.bankaccount.service;

import com.mndiaye.bank.bankaccount.domaine.BankAccount;
import com.mndiaye.bank.bankaccount.enums.OperationType;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    public long getTotalAmountByOperationType(BankAccount bankAccount, OperationType operationType){

        long totalAmount = 0;

        for (int i = 0; i <bankAccount.getOperations().size() ; i++) {
            if (bankAccount.getOperations().get(i).getType().equals(operationType)){
                totalAmount-=Math.abs(bankAccount.getOperations().get(i).getAmount());
            }else {
                totalAmount+=bankAccount.getOperations().get(i).getAmount();
            }
        }
        return totalAmount;
    }

}
