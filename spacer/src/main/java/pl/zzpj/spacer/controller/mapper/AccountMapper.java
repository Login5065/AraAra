package pl.zzpj.spacer.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.zzpj.spacer.controller.dto.AccountDto;
import pl.zzpj.spacer.model.AccountEntity;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto accountEntityToDto(AccountEntity entity);

    AccountEntity accountDtoToEntity(AccountDto accountDto);
}
