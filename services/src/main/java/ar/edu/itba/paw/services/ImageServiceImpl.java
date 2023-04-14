package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ImageService;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImagesDao imagesDao;

    private static byte[] defaultImage() {
        File fi = new File("src/main/static/defaultBookImage.png");

        byte[] fileContent = new byte[0];

        try {
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (Exception e) {
            //
        }
        return fileContent;
    }

    @Override
    public byte[] getPhoto(int id) {
        Optional<byte[]> image = imagesDao.getPhoto(id);
        return image.orElse(defaultImage());
    }
}
