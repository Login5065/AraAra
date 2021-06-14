package pl.zzpj.spacer.dto.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.zzpj.spacer.dto.AccountDto;
import pl.zzpj.spacer.model.Account;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {


    private final AccountMapper mapper = new AccountMapperImpl();

    @Test
    void accountDtoToAccount() {
        //given
        AccountDto accountDto = new AccountDto("Username", "FirstName", "LastName");
        //when
        Account account = mapper.accountDtoToAccount(accountDto);
        //then
        assertNotNull(account);
        assertEquals(accountDto.getUsername(), account.getUsername());
        assertEquals(accountDto.getFirstName(), account.getFirstName());
        assertEquals(accountDto.getLastName(), account.getLastName());
    }

    @Test
    void accountToAccountDto() {
        //given
        Account account = new Account("RandomId", "Username", "Password", "FirstName", "LastName", new HashSet<>());
        //when
        AccountDto accountDto = mapper.accountToAccountDto(account);
        //then
        assertNotNull(accountDto);
        assertEquals(account.getUsername(), accountDto.getUsername());
        assertEquals(account.getFirstName(), accountDto.getFirstName());
        assertEquals(account.getLastName(), accountDto.getLastName());

    }
}