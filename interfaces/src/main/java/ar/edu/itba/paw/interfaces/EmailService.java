package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.Map;

public interface EmailService {

     void sendLenderEmail(final AssetInstance assetInstance, final String borrower,final int lendingId);
     void sendBorrowerEmail(final AssetInstance assetInstance,final User borrower,final int lendingId);
     void sendForgotPasswordEmail(final String email,final String token);

}
