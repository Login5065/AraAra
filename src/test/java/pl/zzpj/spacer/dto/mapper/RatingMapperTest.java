package pl.zzpj.spacer.dto.mapper;

import org.junit.jupiter.api.Test;
import pl.zzpj.spacer.dto.RatingDto;
import pl.zzpj.spacer.model.Rating;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RatingMapperTest {

    private final RatingMapper ratingMapper = new RatingMapperImpl();

    @Test
    void ratingDtoToRating() {
        //given
        RatingDto ratingDto = new RatingDto("OwnerId", "PictureId", 5, LocalDateTime.now().toString());
        //when
        Rating rating = ratingMapper.ratingDtoToRating(ratingDto);
        //then
        assertNotNull(rating);
        assertEquals(ratingDto.getOwner(), rating.getOwner());
        assertEquals(ratingDto.getRating(), rating.getRating());
        assertEquals(ratingDto.getPictureId(), rating.getPictureId());
    }

    @Test
    void ratingToRatingDto() {
        //given
        Rating rating = new Rating("OwnerId", "PictureId", 5);
        //when
        RatingDto ratingDto = ratingMapper.ratingToRatingDto(rating);
        //then
        assertNotNull(ratingDto);
        assertEquals(rating.getOwner(), ratingDto.getOwner());
        assertEquals(rating.getRating(), ratingDto.getRating());
        assertEquals(rating.getPictureId(), ratingDto.getPictureId());
    }
}