package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.miscellaneous.ImageImpl;

import java.util.Optional;

public interface ImagesDao {
    ImageImpl addPhoto(final byte[] photo);

    Optional<byte []> getPhoto(int id);

    ImageImpl getImage(int id);
}
