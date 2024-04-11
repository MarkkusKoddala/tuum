package com.example.tuum.entity;

import lombok.*;

import java.util.List;


@Data
public class Account {
    private Integer accountId;
    private int customerId;
    private String country;
    private List<Balance> balanceList;
}
