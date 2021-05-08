package pl.zzpj.spacer.dto.mapper;

import org.mapstruct.Mapper;
import pl.zzpj.spacer.dto.AccountDto;
import pl.zzpj.spacer.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account accountDtoToAccount(AccountDto accountDto);

    AccountDto accountToAccountDto(Account account);
}
