package com.mndiaye.bank.bankaccount;

import com.mndiaye.bank.bankaccount.controller.BankAccountController;
import com.mndiaye.bank.bankaccount.service.BankAccountService;
import com.mndiaye.bank.bankaccount.service.OperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankAccountApplicationTests {

	@Autowired
	BankAccountController bankAccountController;

	@Autowired
	OperationService operationService;

	@Autowired
	BankAccountService bankAccountService;


	@Test
	void contextLoads() {
		Assertions.assertNotNull(bankAccountController);
		Assertions.assertNotNull(operationService);
		Assertions.assertNotNull(bankAccountService);

	}

}
