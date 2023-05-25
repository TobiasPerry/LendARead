package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ImagesDaoJpa implements ImagesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ImageImpl addPhoto(byte[] photo) {
        ImageImpl image = new ImageImpl(photo);
        entityManager.persist(image);
        entityManager.flush();
        return Optional.of(image.getId());
    }

    @Override
    public Optional<byte[]> getPhoto(int id) {
        ImageImpl image = entityManager.find(ImageImpl.class, id);
        return Optional.ofNullable(image).map(ImageImpl::getPhoto);
    }
}

