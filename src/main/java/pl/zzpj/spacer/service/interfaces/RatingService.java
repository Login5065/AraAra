package pl.zzpj.spacer.service.interfaces;

import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.model.Rating;

import java.util.List;
import java.util.UUID;

public interface RatingService {
    public void addRating(Rating rating) throws AppBaseException;

    public Rating getRating(UUID uuid) throws AccountException;

    public List<Rating> getRatingsByPictureId(String id);

    public List<Rating> getRatingsByUsername(String username);

    public List<Rating> getAll();

    public void editRating(Rating rating) throws AppBaseException;




}
