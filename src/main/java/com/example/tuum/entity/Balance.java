package com.example.tuum.entity;

import com.example.tuum.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


@Data
public class Balance {
    @JsonIgnore
    private int id;
    private Currency currency;
    private double availableAmount;
}
