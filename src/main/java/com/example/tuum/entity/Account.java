package com.example.tuum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Data
public class Account {
    private Integer accountId;
    private Integer customerId;
    @JsonIgnore
    private String country;
    private List<Balance> balanceList;
}
