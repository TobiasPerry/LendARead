package ar.edu.itba.paw.exceptions;

public class UnauthorizedUserException extends Exception{
    public UnauthorizedUserException(String message){
        super(message);
    }
}
