package pl.zzpj.spacer.dto.mapper;

import org.mapstruct.Mapper;
import pl.zzpj.spacer.dto.CommentDto;
import pl.zzpj.spacer.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment commentDtoToComment(CommentDto commentDto);

    CommentDto commentToCommentDto(Comment comment);
}
