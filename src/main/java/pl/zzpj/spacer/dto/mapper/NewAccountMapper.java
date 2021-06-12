package pl.zzpj.spacer.dto.mapper;

import org.mapstruct.Mapper;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.model.Account;

@Mapper(componentModel = "spring")
public interface NewAccountMapper {

    Account newAccountDtoToEntity(NewAccountDto accountDto);
}
