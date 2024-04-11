package com.example.tuum.controller;


import com.example.tuum.dto.CreateTransactionReqDTO;
import com.example.tuum.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController (TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping ("/transaction/create")
    public ResponseEntity <?> createNewTransaction (@RequestBody CreateTransactionReqDTO createTransactionReqDTO){
        return new ResponseEntity<>(transactionService.createNewTransaction(createTransactionReqDTO), HttpStatus.OK);
    }

    @GetMapping("/transaction/{accountId}")
    public ResponseEntity<?> getTransactionList(@PathVariable Integer accountId){
        return new ResponseEntity<>(transactionService.getTransactionListByAccountId(accountId), HttpStatus.OK);
    }
}
