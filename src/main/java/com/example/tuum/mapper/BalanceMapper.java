package com.example.tuum.mapper;


import com.example.tuum.entity.Balance;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BalanceMapper {

    @Select("SELECT currency, available_amount as availableAmount " +
            "FROM balances WHERE account_id = #{accountId}")
    List<Balance> findBalancesByAccountId(int accountId);
}
