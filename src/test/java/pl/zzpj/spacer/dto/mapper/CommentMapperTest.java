package pl.zzpj.spacer.dto.mapper;

import org.junit.jupiter.api.Test;
import pl.zzpj.spacer.dto.CommentDto;
import pl.zzpj.spacer.model.Comment;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private final CommentMapper commentMapper = new CommentMapperImpl();

    @Test
    void commentDtoToComment() {
        //given
        CommentDto commentDto = new CommentDto("OwnerId", "PictureId", "Some Message", LocalDateTime.now().toString());
        //when
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        //then
        assertNotNull(comment);
        assertEquals(commentDto.getOwner(), comment.getOwner());
        assertEquals(commentDto.getPictureId(), comment.getPictureId());
        assertEquals(commentDto.getContent(), comment.getContent());
        assertEquals(commentDto.getDate(), comment.getDate().toString());
    }

    @Test
    void commentToCommentDto() {
        //given
        Comment comment = new Comment("OwnerId", "PictureId", "Some Message");
        //when
        CommentDto commentDto = commentMapper.commentToCommentDto(comment);
        //then
        assertNotNull(commentDto);
        assertEquals(comment.getOwner(), commentDto.getOwner());
        assertEquals(comment.getPictureId(), commentDto.getPictureId());
        assertEquals(comment.getContent(), commentDto.getContent());
        assertEquals(comment.getDate().toString(), commentDto.getDate());
    }
}