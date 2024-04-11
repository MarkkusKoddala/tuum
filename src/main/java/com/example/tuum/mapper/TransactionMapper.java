package com.example.tuum.mapper;


import com.example.tuum.dto.CreateTransactionReqDTO;
import com.example.tuum.dto.TransactionDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO transactions(account_id, amount, currency, direction, description) VALUES \n" +
            "(#{accountId}, #{amount}, #{currency}, #{transactionType}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "transactionId",  keyColumn = "transaction_id")
    int insertNewTransaction(CreateTransactionReqDTO createTransactionReqDTO);

    @Select("SELECT account_id as accountId, transaction_id as transcationId , amount, currency, direction as transactionType , description " +
            "from transactions where account_id = #{accountId}")
    List<TransactionDTO> getTransactionsByAccountId(Integer accountId);
}
