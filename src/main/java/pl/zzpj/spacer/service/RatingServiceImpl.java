package pl.zzpj.spacer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.exception.RatingException;
import pl.zzpj.spacer.model.Account;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.model.Rating;
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.repositories.AccountRepository;
import pl.zzpj.spacer.repositories.CommentRepository;
import pl.zzpj.spacer.repositories.RatingRepository;
import pl.zzpj.spacer.repositories.PictureRepository;
import pl.zzpj.spacer.service.interfaces.RatingService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final AccountRepository accountRepository;

    private final PictureRepository pictureRepository;


    @Override
    public void addRating(Rating rating) throws AppBaseException {
        boolean var = true;

        if(rating.getRating() >5 || rating.getRating() < 0){
            throw RatingException.noSuchRating();
        }else {

            Account account = accountRepository.findByUsername(rating.getOwner()).orElseThrow();
            Picture picture = pictureRepository.findById(rating.getPictureId()).orElseThrow();

            if (!account.getUsername().equals(rating.getOwner()) || !picture.getId().toString().equals(rating.getPictureId()))
                var = false;

            if (var) { //TODO: Implement checks
                ratingRepository.save(rating);
            } else {
                throw AccountException.accountExistsException();
            }

        }
    }

    @Override
    public Rating getRating(UUID uuid) throws AccountException {
        return ratingRepository.findById(uuid).orElseThrow(AccountException::noSuchAccountException);
    }

    @Override
    public List<Rating> getRatingsByPictureId(String id) {
        return ratingRepository.findAllByPictureId(id).orElseThrow();
    }

    @Override
    public List<Rating> getRatingsByUsername(String username) {
        return ratingRepository.findAllByOwner(username).orElseThrow();
    }

    @Override
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    @Override
    public void editRating(Rating rating) throws AppBaseException {
        Optional<Rating> queryRating = ratingRepository.findById(rating.getId());
        if (queryRating.isPresent()) {
            Rating temp = queryRating.get();
            temp.setRating(rating.getRating());
            temp.setOwner(rating.getOwner());
            temp.setPictureId(rating.getPictureId());
            temp.setDate(LocalDateTime.now());
            ratingRepository.save(temp);
        } else {
            throw AccountException.noSuchAccountException();
        }
    }
}
