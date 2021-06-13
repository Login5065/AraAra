package pl.zzpj.spacer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.zzpj.spacer.model.Picture;

import java.util.Date;
import java.util.List;

public interface PictureRepository extends MongoRepository<Picture, String> {

    List<Picture> findAllByCreationDateBetween(Date start, Date end);

    List<Picture> findAllByTagsContaining(String tag);
}
