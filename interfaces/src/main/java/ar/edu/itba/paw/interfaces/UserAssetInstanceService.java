package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;

public interface UserAssetInstanceService {

    UserAssets getUserAssets(String email);

}
