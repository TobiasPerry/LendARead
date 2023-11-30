package ar.edu.itba.paw.exceptions;

public class DayOutOfRangeException extends CustomException{
    public DayOutOfRangeException(int statusCode) {
        super("exception.dayOutOfRange", statusCode);
    }
}
