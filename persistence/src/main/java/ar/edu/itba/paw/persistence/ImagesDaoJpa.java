package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.miscellaneous.Image;
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
    public Image addPhoto(byte[] photo) {
        Image image = new Image(photo);
        entityManager.persist(image);
        entityManager.flush();
        return image;
    }

    @Override
    public Optional<byte[]> getPhoto(int id) {
        Image image = entityManager.find(Image.class, id);
        return Optional.ofNullable(image).map(Image::getPhoto);
    }

    @Override
    public Image getImage(int id) {
        return entityManager.find(Image.class, id);
    }
}

