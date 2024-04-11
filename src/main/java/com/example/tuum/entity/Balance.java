package com.example.tuum.entity;

import com.example.tuum.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;


@Data
public class Balance {
    @JsonIgnore
    private Integer id;
    private Currency currency;
    private Double availableAmount;
}
