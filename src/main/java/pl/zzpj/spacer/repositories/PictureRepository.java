package pl.zzpj.spacer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.zzpj.spacer.model.Picture;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PictureRepository extends MongoRepository<Picture, UUID> {

    List<Picture> findAllByCreationDateBetween(Date start, Date end);

    List<Picture> findAllByTagsContaining(String tag);
}
