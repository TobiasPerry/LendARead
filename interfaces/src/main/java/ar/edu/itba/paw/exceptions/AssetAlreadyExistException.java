package ar.edu.itba.paw.exceptions;

public class AssetAlreadyExistException extends CustomException{

        public AssetAlreadyExistException( int statusCode) {
            super("exception.AssetAlreadyExist", statusCode);
        }
}
