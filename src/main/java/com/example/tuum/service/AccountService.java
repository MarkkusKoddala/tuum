package com.example.tuum.service;

import com.example.tuum.dto.CreateAccountReqDTO;
import com.example.tuum.entity.Account;
import com.example.tuum.enums.Currency;
import com.example.tuum.mapper.AccountMapper;
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
        return accountMapper.getAccountByAccountId(accountId);
    }

    @Transactional
    public Account createNewAccount(CreateAccountReqDTO reqDTO) {
        validateCurrencies(reqDTO.getCurrencyList());
        accountMapper.insertNewAccount(reqDTO);

        reqDTO.getCurrencyList().forEach(currency ->
                accountMapper.insertNewAccountBalance(reqDTO.getAccountId(), currency, 0));

        Account createdAccount = accountMapper.getAccountByAccountId(reqDTO.getAccountId());

        rabbitMQSenderService.sendAccountCreated(createdAccount);

        return createdAccount;
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
