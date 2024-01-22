package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.miscellaneous.Image;

import java.util.Optional;

public interface ImagesDao {
    Image addPhoto(final byte[] photo);


    Optional<Image> getImage(int id);
}
