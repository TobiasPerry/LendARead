package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.LendingsService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.LendingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LendingServiceImpl implements LendingsService {

    @Autowired
    private LendingDao lendingDao;
    @Override
    public boolean lendBook(AssetInstance ai, User borrower, Date devolutionDate) {
        if (ai.getAssetState().canBorrow()){
            return lendingDao.createLending(ai, borrower, devolutionDate);
        }
        return false;
    }
}
