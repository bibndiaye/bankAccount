package com.mndiaye.bank.bankaccount.utils;

public class NoSuchAccountException extends Exception {

    String message;

    public NoSuchAccountException(String message) {
        super(message);
        this.message = message;
    }
}
