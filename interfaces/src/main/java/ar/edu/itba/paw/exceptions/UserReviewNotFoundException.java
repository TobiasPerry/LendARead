package ar.edu.itba.paw.exceptions;

public class UserReviewNotFoundException extends CustomException {
    public UserReviewNotFoundException(int statusCode){
        super("exception.userReviewNotFound", statusCode);
    }
}
