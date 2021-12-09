package com.mndiaye.bank.bankaccount.controller;

import com.mndiaye.bank.bankaccount.domaine.Operation;
import com.mndiaye.bank.bankaccount.domaine.dto.AccountDto;
import com.mndiaye.bank.bankaccount.domaine.dto.OperationCommand;
import com.mndiaye.bank.bankaccount.service.BankAccountService;
import com.mndiaye.bank.bankaccount.service.OperationService;
import com.mndiaye.bank.bankaccount.utils.NoSuchAccountException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/")
public class BankAccountController {
    private final OperationService operationService;

    public BankAccountController(OperationService operationService) {
        this.operationService = operationService;
    }

    @ApiOperation(value = "withdrawall",notes = "perfom a withdrawal on the given account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success",response = AccountDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @PutMapping(value = "{accountId}/withdrawal")
    public AccountDto withdrawal(@PathVariable int accountId,
                                 @RequestBody OperationCommand operationCommand) throws NoSuchAccountException {
        return operationService.doWithdrawal(accountId,operationCommand.getAmount());
    }
    @ApiOperation(value = "deposit",notes = "perfom a deposit on the given account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success",response = AccountDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })

    @PutMapping(value = "{accountId}/deposit")
    public AccountDto deposit(@PathVariable int accountId,
                              @RequestBody OperationCommand operationCommand) throws NoSuchAccountException {
        return operationService.doDeposit(accountId,operationCommand.getAmount());
    }


   @ApiOperation(value = "showOperationsList",notes = "lists all given account operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success",responseContainer = "list",response = Operation.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @GetMapping("{accountId}/history")
    public List<Operation> showOperationsList(@PathVariable int accountId)  {
        return operationService.listAllOperations(accountId);
    }
}

