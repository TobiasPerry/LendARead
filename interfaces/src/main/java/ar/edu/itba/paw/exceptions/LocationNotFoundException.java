package ar.edu.itba.paw.exceptions;
public class LocationNotFoundException extends CustomException{
    public LocationNotFoundException(int statusCode) {
        super("exception.locationNotFound", statusCode);
    }
}
