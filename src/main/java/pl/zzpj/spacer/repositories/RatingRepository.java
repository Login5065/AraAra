package pl.zzpj.spacer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.zzpj.spacer.model.Rating;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends MongoRepository<Rating, String> {
        Optional<List<Rating>> findAllByOwner(String username);
        Optional<List<Rating>> findAllByPictureId(String pictureId);
        Optional<Rating> findById(UUID id);
}