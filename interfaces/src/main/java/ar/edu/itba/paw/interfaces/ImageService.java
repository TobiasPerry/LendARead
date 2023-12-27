package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.miscellaneous.Image;

public interface ImageService {

    byte[] getPhoto(int id)  throws ImageNotFoundException;

    Image getImage(int id) throws ImageNotFoundException;
}
