package com.example.tuum.mapper;


import com.example.tuum.entity.Balance;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BalanceMapper {

    @Select("SELECT currency, available_amount " +
            "FROM balances WHERE account_id = #{account_id}")
    @Results(value = {
            @Result(property = "currency", column = "currency"),
            @Result(property = "availableAmount", column = "available_amount"),
    })
    List<Balance> findBalancesByAccountId(@Param("account_id") int accountId);
}
