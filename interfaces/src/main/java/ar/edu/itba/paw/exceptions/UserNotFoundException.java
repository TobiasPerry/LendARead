package ar.edu.itba.paw.exceptions;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException(int statusCode) {
        super("exception.userNotFound", statusCode);
    }
}
