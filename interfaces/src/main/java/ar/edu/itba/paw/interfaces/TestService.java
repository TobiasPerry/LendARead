package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;

public interface TestService {
    void createAsset(final BookImpl bi);
}
