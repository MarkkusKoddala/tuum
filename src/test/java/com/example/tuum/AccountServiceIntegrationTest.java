package com.example.tuum;

import com.example.tuum.dto.CreateAccountReqDTO;
import com.example.tuum.entity.Account;
import com.example.tuum.entity.Balance;
import com.example.tuum.enums.Currency;
import com.example.tuum.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountService accountService;

    private CreateAccountReqDTO reqDTO;

    @BeforeEach
    public void setup() {
        reqDTO = new CreateAccountReqDTO();
        reqDTO.setCustomerId(1);
        reqDTO.setCountry("TestCountry");
        reqDTO.setCurrencyList(List.of(Currency.EUR, Currency.USD));
    }

    @Test
    public void testCreateNewAccount_Success() {
        reqDTO.setCurrencyList(List.of(Currency.EUR, Currency.USD));

        Account createdAccount = accountService.createNewAccount(reqDTO);

        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getCustomerId()).isEqualTo(reqDTO.getCustomerId());
        assertThat(createdAccount.getCountry()).isEqualTo(reqDTO.getCountry());
        assertThat(createdAccount.getBalanceList()).hasSize(2);

        createdAccount.getBalanceList().forEach(balance ->
                assertThat(List.of(Currency.EUR, Currency.USD)).contains(balance.getCurrency()));
    }

    @Test
    void getAccountByAccountId_AccountDoesNotExist() {
        int nonExistentAccountId = 9999;

        Exception exception = assertThrows(RuntimeException.class, () ->
                accountService.getAccountByAccountId(nonExistentAccountId));

        assertThat(exception.getMessage()).isEqualTo("Account not found");
    }
}
