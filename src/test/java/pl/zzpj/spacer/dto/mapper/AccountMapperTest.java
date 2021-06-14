package pl.zzpj.spacer.dto.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.zzpj.spacer.dto.AccountDto;
import pl.zzpj.spacer.model.Account;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {


    private final AccountMapper mapper = new AccountMapperImpl();

    @Test
    void accountDtoToAccount() {
        //given
        AccountDto accountDto = new AccountDto("Username", "FirstName", "LastName", "email");
        //when
        Account account = mapper.accountDtoToAccount(accountDto);
        //then
        assertNotNull(account);
        assertEquals(accountDto.getUsername(), account.getUsername());
        assertEquals(accountDto.getFirstName(), account.getFirstName());
        assertEquals(accountDto.getLastName(), account.getLastName());
        assertEquals(accountDto.getEmail(), account.getEmail());
    }

    @Test
    void accountToAccountDto() {
        //given
        Account account = new Account("RandomId", "Username", "Password", "FirstName", "LastName", "email", new ArrayList<>());
        //when
        AccountDto accountDto = mapper.accountToAccountDto(account);
        //then
        assertNotNull(accountDto);
        assertEquals(account.getUsername(), accountDto.getUsername());
        assertEquals(account.getFirstName(), accountDto.getFirstName());
        assertEquals(account.getLastName(), accountDto.getLastName());
        assertEquals(account.getEmail(), accountDto.getEmail());
    }
}