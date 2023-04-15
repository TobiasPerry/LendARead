package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.LendingsService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.LendingDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LendingServiceImpl implements LendingsService {

    @Autowired
    private LendingDao lendingDao;

    @Autowired
    private AssetInstanceDao assetInstanceDao;

    @Autowired
    private UserDao userDao;
    @Override
    public boolean lendBook(int assetId, User borrower, Date devolutionDate) {
        Optional<AssetInstance> ai = assetInstanceDao.getAssetInstance(assetId);
        //Optional<Integer>
        if(!ai.isPresent())
            return false;
        if(!ai.get().getAssetState().canBorrow())
            return false;

        lendingDao.createLending(ai.get(),borrower,devolutionDate);
        return false;
    }
}
