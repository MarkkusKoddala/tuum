package com.example.tuum.service;

import com.example.tuum.dto.CreateAccountReqDTO;
import com.example.tuum.entity.Account;
import com.example.tuum.enums.Currency;
import com.example.tuum.mapper.AccountMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;

@Service
public class AccountService {
    private final AccountMapper accountMapper;
    private final RabbitMQSenderService rabbitMQSenderService;
    private static final EnumSet<Currency> VALID_CURRENCIES = EnumSet.allOf(Currency.class);

    public AccountService(AccountMapper accountMapper, RabbitMQSenderService rabbitMQSenderService) {
        this.accountMapper = accountMapper;
        this.rabbitMQSenderService = rabbitMQSenderService;
    }

    public Account getAccountByAccountId(int accountId) {
        Account account = accountMapper.getAccountByAccountId(accountId);
        if (account == null){
            throw new RuntimeException("Account not found");
        }
        return account;
    }

    @Transactional
    public Account createNewAccount(CreateAccountReqDTO reqDTO) {
        validateCurrencies(reqDTO.getCurrencyList());
        int accountId = insertNewAccount(reqDTO);
        insertAccountBalances(reqDTO, accountId);
        Account createdAccount = getAccountByAccountId(accountId);
        sendAccountCreatedMessage(createdAccount);
        return createdAccount;
    }

    private int insertNewAccount(CreateAccountReqDTO reqDTO) {
        accountMapper.insertNewAccount(reqDTO);
        return reqDTO.getAccountId();
    }

    private void insertAccountBalances(CreateAccountReqDTO reqDTO, int accountId) {
        reqDTO.getCurrencyList().forEach(currency ->
                accountMapper.insertNewAccountBalance(accountId, currency, 0));
    }

    private void sendAccountCreatedMessage(Account createdAccount) {
        rabbitMQSenderService.sendAccountCreated(createdAccount);
    }

    private void validateCurrencies(List<Currency> currencyList) {
        if (!currencyList.stream().allMatch(this::isValidCurrency)) {
            throw new IllegalArgumentException("Invalid currency");
        }
    }

    private boolean isValidCurrency(Currency currency) {
        return VALID_CURRENCIES.contains(currency);
    }
}
