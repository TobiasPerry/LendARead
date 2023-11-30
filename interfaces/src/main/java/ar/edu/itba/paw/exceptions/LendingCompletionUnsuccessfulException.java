package ar.edu.itba.paw.exceptions;

public class LendingCompletionUnsuccessfulException extends CustomException{
    public LendingCompletionUnsuccessfulException(int statusCode) {
        super("exception.lendingCompletionUnsuccessful", statusCode);
    }
}
