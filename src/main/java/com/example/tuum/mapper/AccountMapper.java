package com.example.tuum.mapper;


import com.example.tuum.dto.CreateAccountReqDTO;
import com.example.tuum.entity.Account;
import com.example.tuum.enums.Currency;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    @Select("SELECT account_id, customer_id as customerId, country FROM accounts WHERE account_id = #{accountId}")
    @Results(value = {
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "balanceList", column = "account_id",
                    many = @Many(select = "com.example.tuum.mapper.BalanceMapper.findBalancesByAccountId"))
    })
    Account getAccountByAccountId(int accountId);

    @Insert("INSERT INTO accounts (customer_id, country) VALUES (#{customerId}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "accountId",  keyColumn = "account_id")
    void insertNewAccount(CreateAccountReqDTO reqDTO);

    @Insert("INSERT INTO balances(account_id, currency, available_amount) VALUES (#{accountId}, #{currency}, #{amount})")
    void insertNewAccountBalance(Integer accountId, Currency currency, Integer amount);

    @Update("UPDATE balances SET available_amount = #{newBalance} WHERE account_id = #{accountId} AND currency = #{currency}")
    void updateAccountBalance(int accountId, double newBalance, Currency currency);
}
