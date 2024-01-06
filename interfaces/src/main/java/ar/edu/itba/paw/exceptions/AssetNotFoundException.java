package ar.edu.itba.paw.exceptions;

public class AssetNotFoundException extends CustomException{
    public AssetNotFoundException(int statusCode){
        super("exception.assetNotFound", statusCode);
    }
}
