package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AssetAvailabilityServiceImpl implements AssetAvailabilityService {
    private final AssetAvailabilityDao lendingDao;

    private final AssetInstanceDao assetInstanceDao;

    private final UserDao userDao;

    @Autowired
    public AssetAvailabilityServiceImpl(AssetAvailabilityDao lendingDao, AssetInstanceDao assetInstanceDao, UserDao userDao) {
        this.lendingDao = lendingDao;
        this.assetInstanceDao = assetInstanceDao;
        this.userDao = userDao;

    }

    @Override
    public boolean borrowAsset(int assetId, User borrower, LocalDate devolutionDate) {
        Optional<AssetInstance> ai = assetInstanceDao.getAssetInstance(assetId);
        Optional<Integer> userID = userDao.addUser(borrower);
        if(!ai.isPresent() || !userID.isPresent())
            return false;
        if(!ai.get().getAssetState().canBorrow())
            return false;
        assetInstanceDao.changeStatus(assetId, AssetState.BORROWED);
        boolean saved = lendingDao.borrowAssetInstance(ai.get().getId(),userID.get(),LocalDate.now(),devolutionDate);
        if (saved) {
            EmailServiceImpl.sendEmail(borrower.getEmail(), "Préstamo de Libro:  " + ai.get().getBook().getName(), "Hola.\nEl préstamo del libro" + ai.get().getBook().getName() + " se confirmado. Por favor recordá los datos para retirarlo:\n" + ai.get().getLocation().toString());
        }
        return saved;
    }
}
