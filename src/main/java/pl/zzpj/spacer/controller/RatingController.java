package pl.zzpj.spacer.controller;



import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.spacer.dto.CommentDto;
import pl.zzpj.spacer.dto.RatingDto;
import pl.zzpj.spacer.dto.mapper.RatingMapper;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.RatingException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Rating;
import pl.zzpj.spacer.service.interfaces.RatingService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    private final RatingMapper ratingMapper;

    @PostMapping("rate")
    public ResponseEntity<String> addRating(@RequestBody RatingDto ratingDto){
        try{
            ratingService.addRating(ratingMapper.ratingDtoToRating(ratingDto));
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("rate/{id}")
    public ResponseEntity getRating(@PathVariable("id") String id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ratingService.getRating(UUID.fromString(id)));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("rate/picture/{id}")
    public  ResponseEntity<List<RatingDto>> getRatingsByPictureId(@PathVariable("id") String pictureId){
        List<Rating> ratings = ratingService.getRatingsByPictureId(pictureId);
        List<RatingDto> ratingDtos = ratings.stream()
                .map(ratingMapper::ratingToRatingDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ratingDtos);
    }

}
