package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.LendingDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class AssetAvailabilityAvailabilityServiceImpl implements AssetAvailabilityService {
    @Autowired
    private LendingDao lendingDao;

    @Autowired
    private AssetInstanceDao assetInstanceDao;

    @Autowired
    private UserDao userDao;

    @Override
    public boolean borrowAsset(int assetId, User borrower, LocalDate devolutionDate) {
        Optional<AssetInstance> ai = assetInstanceDao.getAssetInstance(assetId);
        Optional<Integer> userID = userDao.addUser(borrower);
        if(!ai.isPresent() || !userID.isPresent())
            return false;
        if(!ai.get().getAssetState().canBorrow())
            return false;
        assetInstanceDao.changeStatus(assetId, AssetState.BORROWED);
        return lendingDao.createLending(ai.get().getId(),userID.get(),devolutionDate);
    }
}
