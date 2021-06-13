package pl.zzpj.spacer.service.interfaces;

import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.model.Picture;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PictureService {
    void addPicture(Picture picture) throws PictureException;

    Picture getPicture(UUID uuid) throws PictureException;

    List<Picture> getAll();

    List<Picture> getAllBetweenDate(Date start, Date end);

    List<Picture> getAllByTag(String tag);

    void addPictureTags(UUID pictureId, Set<String> newTags) throws PictureException;

    void deletePictureTags(UUID pictureId, Set<String> tagsToDelete) throws PictureException;
}
