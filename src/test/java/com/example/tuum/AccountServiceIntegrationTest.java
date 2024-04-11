package com.example.tuum;

import com.example.tuum.dto.CreateAccountReqDTO;
import com.example.tuum.entity.Account;
import com.example.tuum.entity.Balance;
import com.example.tuum.enums.Currency;
import com.example.tuum.mapper.AccountMapper;
import com.example.tuum.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper; // Assuming you want to verify DB state directly

    @Test
    public void testCreateNewAccount_Success() {
        // Setup
        CreateAccountReqDTO reqDTO = new CreateAccountReqDTO();
        reqDTO.setAccountId(1);
        reqDTO.setCustomerId(123);
        reqDTO.setCountry("TestCountry");
        reqDTO.setCurrencyList(Arrays.asList(Currency.EUR, Currency.USD));

        // Execute
        Account createdAccount = accountService.createNewAccount(reqDTO);

        // Verify
        assertNotNull(createdAccount);
        assertEquals(reqDTO.getAccountId(), createdAccount.getAccountId());
        // Further assertions to validate the account details...

        // Directly verify the database state if necessary
        List<Balance> balances = accountService.getAccountByAccountId(createdAccount.getAccountId()).getBalanceList();
        assertEquals(2, balances.size());
    }
}
