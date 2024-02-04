package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.miscellaneous.Image;

import java.util.Optional;

public interface ImageService {

    Optional<Image> getImage(int id) ;

    Image addImage(byte[] image);
}
