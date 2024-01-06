package ar.edu.itba.paw.exceptions;

public class CustomException extends Exception{
    private final String message;
    private  int statusCode;

    public CustomException(String message, int statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
       this.statusCode = statusCode;
    }
}
