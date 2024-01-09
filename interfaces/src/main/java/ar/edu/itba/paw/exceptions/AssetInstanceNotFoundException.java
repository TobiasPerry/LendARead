package ar.edu.itba.paw.exceptions;

public class AssetInstanceNotFoundException extends CustomException{
    public AssetInstanceNotFoundException(int statusCode){
        super("exception.assetInstanceNotFound", statusCode);
    }
}
