package com.example.tuum.mapper;


import com.example.tuum.dto.CreateAccountReqDTO;
import com.example.tuum.entity.Account;
import com.example.tuum.entity.Balance;
import com.example.tuum.enums.Currency;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("SELECT account_id, customer_id, country FROM accounts WHERE account_id = #{account_id}")
    @Results(value = {
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "country", column = "country"),
            @Result(property = "balanceList", column = "account_id",
                    many = @Many(select = "com.example.tuum.mapper.BalanceMapper.findBalancesByAccountId"))
    })
    Account getAccountByAccountId(@Param("account_id") int accountId);


    @Insert("INSERT INTO accounts (customer_id, country) VALUES (#{customerId}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId",  keyColumn = "account_id")
    void insertNewAccount(CreateAccountReqDTO reqDTO);


    @Insert("INSERT INTO balances(account_id, currency, available_amount) VALUES (#{accountId}, #{currency}, #{amount})")
    void insertNewAccountBalance(Integer accountId, Currency currency, Integer amount);


    @Select("SELECT balance_id, currency, available_amount from balances where account_id = #{accountId}")
    List<Balance> findBalancesByAccountId(Integer accountId);
}
