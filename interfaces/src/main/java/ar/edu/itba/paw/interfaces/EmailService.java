package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import java.util.Locale;

public interface EmailService {

    void sendLenderEmail(final AssetInstanceImpl assetInstance, final String borrower, final Long lendingId, final Locale locale);

    void sendBorrowerEmail(final AssetInstanceImpl assetInstance, final UserImpl borrower, final Long lendingId, final Locale locale);

    void sendForgotPasswordEmail(final String email, final String token, final Locale locale);

    void sendRejectedEmail(final AssetInstanceImpl assetInstance, final UserImpl borrower, final Long lendingId, final Locale locale);

    void sendReviewBorrower(AssetInstanceImpl assetInstance, UserImpl borrower, Long lendingId, Locale locale);

    void sendReviewLender(AssetInstanceImpl assetInstance, UserImpl borrower, Long lendingId, Locale locale);

}
