package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;

public interface ImageService {

    byte[] getPhoto(int id)  throws ImageNotFoundException;

    ImageImpl getImage(int id) throws ImageNotFoundException;
}
