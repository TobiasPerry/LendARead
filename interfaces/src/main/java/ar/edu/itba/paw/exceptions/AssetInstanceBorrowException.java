package ar.edu.itba.paw.exceptions;

public class AssetInstanceBorrowException extends CustomException{
    public AssetInstanceBorrowException(int statusCode){

        super("exception.assetInstanceBorrowed", statusCode);
    }
}
