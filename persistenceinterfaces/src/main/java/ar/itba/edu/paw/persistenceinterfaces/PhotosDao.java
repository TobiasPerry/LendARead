package ar.itba.edu.paw.persistenceinterfaces;

import java.util.Optional;

public interface PhotosDao {
    Optional<Integer> addPhoto(final byte[] photo);

    Optional<byte []> getPhoto(int id);
}
