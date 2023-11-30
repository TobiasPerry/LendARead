package ar.edu.itba.paw.exceptions;

public class LendingNotFoundException extends CustomException{
    public LendingNotFoundException(int statusCode){
        super("exception.lendingNotFound", statusCode);
    }
}
