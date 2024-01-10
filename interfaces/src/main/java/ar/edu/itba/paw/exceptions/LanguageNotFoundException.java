package ar.edu.itba.paw.exceptions;

public class LanguageNotFoundException extends CustomException{

    public LanguageNotFoundException( int statusCode) {
        super("{exception.LanguageNotFound}", statusCode);
    }
}
