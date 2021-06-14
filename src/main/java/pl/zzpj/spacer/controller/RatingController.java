package pl.zzpj.spacer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.spacer.dto.RatingDto;
import pl.zzpj.spacer.dto.mapper.RatingMapper;
import pl.zzpj.spacer.exception.AccountException;
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

    @PostMapping("rating")
    public ResponseEntity<String> addRating(@RequestBody RatingDto ratingDto){
        try{
            ratingService.addRating(ratingMapper.ratingDtoToRating(ratingDto));
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("rating/{id}")
    public ResponseEntity getRating(@PathVariable("id") String id){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ratingService.getRating(UUID.fromString(id)));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("rating/picture/{id}")
    public  ResponseEntity<List<RatingDto>> getRatingsByPictureId(@PathVariable("id") String pictureId){
        List<Rating> ratings = ratingService.getRatingsByPictureId(pictureId);
        List<RatingDto> ratingDtos = ratings.stream()
                .map(ratingMapper::ratingToRatingDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ratingDtos);
    }

    @GetMapping("rating/username/{username}")
    public ResponseEntity<List<RatingDto>> getRatingsByUsername(@PathVariable("username") String username) {
        List<Rating> ratings = ratingService.getRatingsByUsername(username);
        List<RatingDto> ratingDtos = ratings.stream()
                .map(ratingMapper::ratingToRatingDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ratingDtos);
    }

    @GetMapping("ratings")
    public ResponseEntity<List<RatingDto>> getRatingsAll() {
        List<Rating> ratings = ratingService.getAll();
        List<RatingDto> ratingDtos = ratings.stream()
                .map(ratingMapper::ratingToRatingDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ratingDtos);
    }

    @PutMapping("rating/edit")
    public ResponseEntity editRating(@RequestBody RatingDto ratingDto) {
        try {
            List<Rating> ratings = ratingService.getRatingsByUsername(ratingDto.getOwner())
                    .stream().filter((Rating candidate) -> (
                            candidate.getPictureId().equals(ratingDto.getPictureId()) &&
                                    candidate.getOwner().equals(ratingDto.getOwner()) &&
                                    candidate.getDate().toString().equals(ratingDto.getDate())))
                    .collect(Collectors.toList());

            if (ratings.size() != 1)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Found more than one rating. Unexpected");

            Rating editable = ratings.get(0);

            editable.setRating(ratingDto.getRating());
            ratingService.editRating(editable);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



}
