package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImagesDao imagesDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    @Autowired
    public ImageServiceImpl(final ImagesDao imagesDao) {
        this.imagesDao = imagesDao;
    }

    @Transactional(readOnly = true)
    @Override
    public byte[] getPhoto(final int id) throws ImageNotFoundException {
        Optional<byte[]> image = imagesDao.getPhoto(id);
        if (!image.isPresent()) {
            LOGGER.error("Could not found image with id = {}", id);
            throw new ImageNotFoundException("Image not found");
        }
        return image.get();
    }

    @Transactional(readOnly = true)

    @Override
    public ImageImpl getImage(int id) throws ImageNotFoundException {
        ImageImpl image = imagesDao.getImage(id);
        if (image == null) {
            LOGGER.error("Could not found image with id = {}", id);
            throw new ImageNotFoundException("Image not found");
        }
        return image;
    }
}
