package pl.zzpj.spacer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.repositories.PictureRepository;
import pl.zzpj.spacer.service.interfaces.PictureService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Override
    public void addPicture(Picture picture) throws PictureException {
        if (pictureRepository.findById(picture.getId()).isEmpty()) {
            pictureRepository.save(picture);
        } else {
            throw PictureException.pictureExistsException();
        }
    }

    @Override
    public Picture getPicture(String uuid) throws PictureException {
        return pictureRepository.findById(uuid).orElseThrow(PictureException::noSuchPictureException);
    }

    @Override
    public List<Picture> getAll() {
        return pictureRepository.findAll();
    }

    @Override
    public List<Picture> getAllBetweenDate(Date start, Date end) {
        return pictureRepository.findAllByCreationDateBetween(start, end);
    }

    @Override
    public List<Picture> getAllByTag(String tag) {
        return pictureRepository.findAllByTagsContaining(tag);
    }

    @Override
    public void addPictureTags(String pictureId, Set<String> newTags) throws PictureException {
        Optional<Picture> queryPicture = pictureRepository.findById(pictureId);
        if (queryPicture.isPresent()) {
            Picture tmp = queryPicture.get();
            tmp.getTags().addAll(newTags);
            pictureRepository.save(tmp);
        } else {
            throw PictureException.noSuchPictureException();
        }
    }

    @Override
    public void deletePictureTags(String pictureId, Set<String> tagsToDelete) throws PictureException {
        Optional<Picture> queryPicture = pictureRepository.findById(pictureId);
        if (queryPicture.isPresent()) {
            Picture tmp = queryPicture.get();
            tmp.getTags().removeAll(tagsToDelete);
            pictureRepository.save(tmp);
        } else {
            throw PictureException.noSuchPictureException();
        }
    }


}
