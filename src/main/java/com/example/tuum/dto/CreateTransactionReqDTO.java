package com.example.tuum.dto;

import com.example.tuum.enums.Currency;
import com.example.tuum.enums.TransactionType;
import lombok.Data;

@Data
public class CreateTransactionReqDTO {
    private Integer transactionId;
    private Integer accountId;
    private Double amount;
    private Currency currency;
    private TransactionType transactionType;
    private String description;
}
