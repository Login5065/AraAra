package pl.zzpj.spacer.dto.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.zzpj.spacer.dto.AccountDto;
import pl.zzpj.spacer.model.Account;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-08T20:56:03+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Red Hat, Inc.)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account accountDtoToAccount(AccountDto accountDto) {
        if ( accountDto == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String username = null;

        firstName = accountDto.getFirstName();
        lastName = accountDto.getLastName();
        username = accountDto.getUsername();

        String password = null;

        Account account = new Account( username, password, firstName, lastName );

        return account;
    }

    @Override
    public AccountDto accountToAccountDto(Account account) {
        if ( account == null ) {
            return null;
        }

        String username = null;
        String firstName = null;
        String lastName = null;

        username = account.getUsername();
        firstName = account.getFirstName();
        lastName = account.getLastName();

        AccountDto accountDto = new AccountDto( username, firstName, lastName );

        return accountDto;
    }
}
