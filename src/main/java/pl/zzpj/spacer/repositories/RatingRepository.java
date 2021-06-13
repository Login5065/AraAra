package pl.zzpj.spacer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.zzpj.spacer.model.Account;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.model.Rating;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends MongoRepository<Rating, String> {
        Optional<Rating> findAllByOwner(String username);
        Optional<List<Rating>> findAllByPictureId(String pictureId);
        Optional<List<Rating>> find();
}