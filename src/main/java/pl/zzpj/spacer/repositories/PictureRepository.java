package pl.zzpj.spacer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.zzpj.spacer.model.Picture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PictureRepository extends MongoRepository<Picture, UUID> {

    List<Picture> findAllByAddDateBetween(LocalDateTime start, LocalDateTime end);

    List<Picture> findAllByTagsContaining(String tag);
}
