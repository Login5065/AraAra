package pl.zzpj.spacer.dto.mapper;


import org.mapstruct.Mapper;
import pl.zzpj.spacer.dto.RatingDto;
import pl.zzpj.spacer.model.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    Rating ratingDtoToRating(RatingDto ratingDto);

    RatingDto ratingToRatingDto(Rating rating);

}
