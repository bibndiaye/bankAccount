package com.mndiaye.bank.bankaccount.domaine;

public enum OperationType {

    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal");

    String operation;

    OperationType(String operation){
        this.operation=operation;
    }
}
