package ar.edu.itba.paw.exceptions;

public class ImageNotFoundException extends CustomException{
    public ImageNotFoundException(int statusCode) {
        super("exception.imageNotFound", statusCode);
    }
}
