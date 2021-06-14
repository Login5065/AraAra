package pl.zzpj.spacer.dto.mapper;

import org.junit.jupiter.api.Test;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.model.Account;

import static org.junit.jupiter.api.Assertions.*;

class NewAccountMapperTest {

    private final NewAccountMapper newAccountMapper = new NewAccountMapperImpl();

    @Test
    void newAccountDtoToEntity() {

        //given
        NewAccountDto newAccountDto = new NewAccountDto("Username", "Password", "FirstName", "LastName");
        //when
        Account account = newAccountMapper.newAccountDtoToEntity(newAccountDto);
        //then
        assertNotNull(account);
        assertEquals(newAccountDto.getUsername(), account.getUsername());
        assertEquals(newAccountDto.getFirstName(), account.getFirstName());
        assertEquals(newAccountDto.getLastName(), account.getLastName());
    }
}