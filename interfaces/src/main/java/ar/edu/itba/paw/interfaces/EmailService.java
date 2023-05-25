package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

public interface EmailService {

    void sendLenderEmail(final AssetInstanceImpl assetInstance, final String borrower, final int lendingId);

    void sendBorrowerEmail(final AssetInstance assetInstance, final UserImpl borrower, final int lendingId);

    void sendForgotPasswordEmail(final String email, final String token);

}
