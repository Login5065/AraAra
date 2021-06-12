package pl.zzpj.spacer.dto.mapper;

import org.mapstruct.Mapper;
import pl.zzpj.spacer.dto.PictureDto;
import pl.zzpj.spacer.model.Picture;

@Mapper(componentModel = "spring")
public interface PictureMapper {

    Picture accountDtoToAccount(PictureDto accountDto);

    PictureDto accountToAccountDto(Picture account);
}
