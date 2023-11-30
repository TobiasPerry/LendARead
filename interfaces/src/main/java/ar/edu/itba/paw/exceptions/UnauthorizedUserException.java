package ar.edu.itba.paw.exceptions;

public class UnauthorizedUserException extends CustomException{
    public UnauthorizedUserException(int statusCode){
        super("exception.unauthorizedUser", statusCode);
    }
}
