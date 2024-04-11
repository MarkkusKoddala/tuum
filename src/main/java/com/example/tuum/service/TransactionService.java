package com.example.tuum.service;


import com.example.tuum.dto.CreateTransactionOutputDTO;
import com.example.tuum.dto.CreateTransactionReqDTO;
import com.example.tuum.dto.TransactionDTO;
import com.example.tuum.entity.Account;
import com.example.tuum.entity.Balance;
import com.example.tuum.enums.Currency;
import com.example.tuum.enums.TransactionType;
import com.example.tuum.mapper.AccountMapper;
import com.example.tuum.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionMapper transactionMapper;
    private final AccountMapper accountMapper;
    private static final EnumSet<Currency> VALID_CURRENCIES = EnumSet.allOf(Currency.class);
    private static final EnumSet<TransactionType> VALID_TRANSACTIONS = EnumSet.allOf(TransactionType.class);


    @Autowired
    public TransactionService(TransactionMapper transactionMapper, AccountMapper accountMapper) {
        this.transactionMapper = transactionMapper;
        this.accountMapper = accountMapper;

    }

    @Transactional
    public CreateTransactionOutputDTO createNewTransaction (CreateTransactionReqDTO createTransactionReqDTO){
        validateInputs(createTransactionReqDTO);
        return updateAccount(createTransactionReqDTO);
    }

    private CreateTransactionOutputDTO updateAccount(CreateTransactionReqDTO createTransactionReqDTO) {
        Account currentAccount = accountMapper.getAccountByAccountId(createTransactionReqDTO.getAccountId());
        Balance currentBalance = findBalanceForCurrency(currentAccount, createTransactionReqDTO.getCurrency());
        isSufficientTransaction(createTransactionReqDTO, currentBalance);

        double newBalance = calculateNewBalance(createTransactionReqDTO, currentBalance);
        int transactionId = transactionMapper.insertNewTransaction(createTransactionReqDTO);
        accountMapper.updateAccountBalance(currentAccount.getAccountId(), newBalance, currentBalance.getCurrency());

        return new CreateTransactionOutputDTO(currentAccount.getAccountId(), transactionId, createTransactionReqDTO.getAmount(),
                createTransactionReqDTO.getCurrency(), createTransactionReqDTO.getTransactionType(), createTransactionReqDTO.getDescription(), newBalance);

    }

    private double calculateNewBalance(CreateTransactionReqDTO createTransactionReqDTO, Balance currentBalance) {
        double currentAmount = currentBalance.getAvailableAmount();
        double transactionAmount = createTransactionReqDTO.getAmount();
        return createTransactionReqDTO.getTransactionType() == TransactionType.IN ?
                currentAmount + transactionAmount : currentAmount - transactionAmount;
    }

    private Balance findBalanceForCurrency(Account account, Currency currency) {
        for (Balance balance : account.getBalanceList()) {
            if (balance.getCurrency() == currency)
                return balance;
        }
        return null;
    }

    private void validateInputs(CreateTransactionReqDTO createTransactionReqDTO) {
        if (createTransactionReqDTO.getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        if (createTransactionReqDTO.getCurrency() == null || !VALID_CURRENCIES.contains(createTransactionReqDTO.getCurrency())) {
            throw new IllegalArgumentException("Invalid currency");
        }
        if (createTransactionReqDTO.getTransactionType() == null || !VALID_TRANSACTIONS.contains(createTransactionReqDTO.getTransactionType())) {
            throw new IllegalArgumentException("Invalid direction");
        }
        if (createTransactionReqDTO.getDescription() == null || createTransactionReqDTO.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description missing");
        }
        if (createTransactionReqDTO.getAccountId() == null) {
            throw new IllegalArgumentException("Account missing");
        }
    }

    private void isSufficientTransaction (CreateTransactionReqDTO createTransactionReqDTO, Balance currentBalance){
        if (currentBalance == null)
            throw new IllegalArgumentException("Currency not supported for this account");

        if(createTransactionReqDTO.getTransactionType() == TransactionType.OUT && createTransactionReqDTO.getAmount() > currentBalance.getAvailableAmount()) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    public List<TransactionDTO> getTransactionListByAccountId(Integer accountId) {
        List<TransactionDTO> transactionDTOS = transactionMapper.getTransactionsByAccountId(accountId);

        if(transactionDTOS.isEmpty())
            throw  new IllegalArgumentException("Wrong Account ID");

        return transactionDTOS;
    }
}
