package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;

public interface ImageService {

    byte[] getPhoto(final int id)  throws ImageNotFoundException;
}
