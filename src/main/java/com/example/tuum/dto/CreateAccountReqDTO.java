package com.example.tuum.dto;

import com.example.tuum.enums.Currency;
import lombok.Data;

import java.util.List;

@Data
public class CreateAccountReqDTO {
    private Integer accountId;
    private int customerId;
    private String country;
    private List<Currency> currencyList;
}
