package com.example.tuum.controller;


import com.example.tuum.dto.CreateAccountReqDTO;
import com.example.tuum.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping( "/accounts/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable String accountId){
        return new ResponseEntity<>(accountService.getAccountByAccountId(Integer.parseInt(accountId)), HttpStatus.OK);
    }

    @PostMapping("/accounts/add")
    public ResponseEntity<?> createNewAccount(@RequestBody CreateAccountReqDTO account){
        return new ResponseEntity<>(accountService.createNewAccount(account), HttpStatus.OK);
    }

}
