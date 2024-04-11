package com.example.tuum.dto;

import com.example.tuum.enums.Currency;
import com.example.tuum.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTransactionOutputDTO {
    private Integer accountId;
    private Integer transcationId;
    private Double amount;
    private Currency currency;
    private TransactionType transactionType;
    public String description;
    public Double balanceAfterTransaction;
}
