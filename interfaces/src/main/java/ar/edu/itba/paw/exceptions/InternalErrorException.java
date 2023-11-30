package ar.edu.itba.paw.exceptions;

public class InternalErrorException extends CustomException{
    public InternalErrorException(int statusCode) {
        super("exception.internalError", statusCode);
    }
}
