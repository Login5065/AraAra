package pl.zzpj.spacer.dto.mapper;

import org.junit.jupiter.api.Test;
import pl.zzpj.spacer.dto.PictureDto;
import pl.zzpj.spacer.model.Picture;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PictureMapperTest {

    private final PictureMapper pictureMapper = new PictureMapperImpl();

    @Test
    void toEntity() {
        //given
        PictureDto pictureDto = new PictureDto("RandomId", "www.somberly.com", "Some Title", new HashSet<>(List.of("test")), new Date());
        //when
        Picture picture = pictureMapper.toEntity(pictureDto);
        //then
        assertNotNull(picture);
        assertNull(picture.getId());
        assertEquals(pictureDto.getCreationDate(), picture.getCreationDate());
        assertEquals(pictureDto.getTitle(), picture.getTitle());
        assertEquals(pictureDto.getUrl(), picture.getUrl());

    }

    @Test
    void toDto() {
        //given
        Picture picture = new Picture("RandomId", "www.somberly.com", "Some Title", new HashSet<>(List.of("test")), new Date());
        //when
        PictureDto pictureDto = pictureMapper.toDto(picture);
        //then
        assertNotNull(pictureDto);
        assertEquals(picture.getId(),pictureDto.getId());
        assertEquals(picture.getCreationDate(), pictureDto.getCreationDate());
        assertEquals(picture.getTitle(), pictureDto.getTitle());
        assertEquals(picture.getUrl(), pictureDto.getUrl());
    }
}