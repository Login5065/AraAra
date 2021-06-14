package pl.zzpj.spacer.service.interfaces;

import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.model.Rating;

import java.util.List;
import java.util.UUID;

public interface RatingService {
     void addRating(Rating rating) throws AppBaseException;

     Rating getRating(UUID uuid) throws AccountException;

     List<Rating> getRatingsByPictureId(String id);

     List<Rating> getRatingsByUsername(String username);

     List<Rating> getAll();

     void editRating(Rating rating) throws AppBaseException;




}
