package pl.zzpj.spacer.dto.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.model.Account;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-08T20:56:03+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Red Hat, Inc.)"
)
@Component
public class NewAccountMapperImpl implements NewAccountMapper {

    @Override
    public Account newAccountDtoToEntity(NewAccountDto accountDto) {
        if ( accountDto == null ) {
            return null;
        }

        String password = null;
        String firstName = null;
        String lastName = null;
        String username = null;

        password = accountDto.getPassword();
        firstName = accountDto.getFirstName();
        lastName = accountDto.getLastName();
        username = accountDto.getUsername();

        Account account = new Account( username, password, firstName, lastName );

        return account;
    }
}
