package ar.edu.itba.paw.exceptions;

public class AssetInstanceReviewNotFoundException extends CustomException{
    public AssetInstanceReviewNotFoundException(int statusCode){
        super("exception.assetInstanceReviewNotFound", statusCode);
    }
}
