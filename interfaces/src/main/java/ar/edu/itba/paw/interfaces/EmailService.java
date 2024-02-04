package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.userContext.User;

import java.util.Locale;

public interface EmailService {

    void sendLenderEmail(final AssetInstance assetInstance, final String borrower, final Long lendingId, final Locale locale);

    void sendBorrowerEmail(final AssetInstance assetInstance, final User borrower, final Long lendingId, final Locale locale);

    void sendForgotPasswordEmail(final String email, final String token, final Locale locale);

    void sendRejectedEmail(final AssetInstance assetInstance, final User borrower, final Long lendingId, final Locale locale);

    void sendCanceledEmail(AssetInstance assetInstance, Long lendingId, Locale locale);

    void sendReviewBorrower(AssetInstance assetInstance, User borrower, User lender, Long lendingId, Locale locale);

    void sendReviewLender(AssetInstance assetInstance, User lender, User borrower, Long lendingId, Locale locale);

    void sendRemindLendingToLender(Lending lending, User lender, User borrower, Locale locale);

    void sendRemindReturnToLender(Lending lending, User lender, User borrower, Locale locale);
}
