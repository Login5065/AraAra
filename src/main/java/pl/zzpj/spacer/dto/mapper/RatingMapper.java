package pl.zzpj.spacer.dto.mapper;


import org.mapstruct.Mapper;
import pl.zzpj.spacer.dto.CommentDto;
import pl.zzpj.spacer.dto.RatingDto;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.model.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    Comment commentDtoToComment(CommentDto commentDto);

    CommentDto commentToCommentDto(Comment comment);

}
