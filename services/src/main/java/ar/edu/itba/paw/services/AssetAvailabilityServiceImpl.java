package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetInstanceBorrowException;
import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.DayOutOfRangeException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.BorrowedAssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class AssetAvailabilityServiceImpl implements AssetAvailabilityService {
    private final AssetAvailabilityDao lendingDao;

    private final AssetInstanceDao assetInstanceDao;

    private final UserDao userDao;

    private final EmailService emailService;

    @Autowired
    public AssetAvailabilityServiceImpl(final AssetAvailabilityDao lendingDao,final AssetInstanceDao assetInstanceDao,final UserDao userDao,final EmailService emailService) {
        this.lendingDao = lendingDao;
        this.assetInstanceDao = assetInstanceDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public void borrowAsset(final int assetId,final String borrower, final LocalDate devolutionDate) throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException {
        Optional<AssetInstance> ai = assetInstanceDao.getAssetInstance(assetId);
        Optional<User> user = userDao.getUser(borrower);
        if(!ai.isPresent())
            throw  new AssetInstanceBorrowException("The assetInstance or the user not found");
        if(!user.isPresent())
            throw new UserNotFoundException("The user not found");
        if(!ai.get().getAssetState().isPublic())
            throw  new AssetInstanceBorrowException("The assetInstance is not public");
        if (LocalDate.now().plusDays(ai.get().getMaxDays()).isBefore(devolutionDate) )
            throw  new DayOutOfRangeException();


        assetInstanceDao.changeStatus(assetId, AssetState.PENDING);
        boolean saved = lendingDao.borrowAssetInstance(ai.get().getId(),user.get().getId(),LocalDate.now(),devolutionDate);
        if (saved) {
            emailService.sendBorrowerEmail(ai.get(), user.get());
            emailService.sendLenderEmail(ai.get(), borrower);
        }else{
            throw new AssetInstanceBorrowException("Asset cant be lending");
        }
    }

    @Transactional
    @Override
    public void setAssetPrivate(final int assetId) throws AssetInstanceNotFoundException {
        if(!assetInstanceDao.changeStatus(assetId, AssetState.PRIVATE))
            throw new AssetInstanceNotFoundException("Asset instance not found");
    }

    @Transactional
    @Override
    public void setAssetPublic(final int assetId) throws AssetInstanceNotFoundException {
        if(!assetInstanceDao.changeStatus(assetId, AssetState.PUBLIC))
            throw new AssetInstanceNotFoundException("Asset instance not found");
    }

    @Transactional(readOnly = true)
    @Override
    public List<BorrowedAssetInstance> getAllBorrowedAssetsInstances() {
        List<LendingDetails> lendingDetails =  lendingDao.getAllLendings();
        List<BorrowedAssetInstance> borrowedAssetInstances = new ArrayList<>();

        for (LendingDetails lendingDetail : lendingDetails) {
            Optional<AssetInstance> assetInstance = assetInstanceDao.getAssetInstance(lendingDetail.getAssetInstanceId());
            if (assetInstance.isPresent()) {
                String borrower = userDao.getUser(lendingDetail.getBorrowerId()).get().getName();
                borrowedAssetInstances.add(new BorrowedAssetInstanceImpl(assetInstance.get(), lendingDetail.getReturnDate().toString(), borrower));
            }
        }

        return borrowedAssetInstances;
    }

    @Transactional(readOnly = true)
    @Override
    public void returnAsset(int assetId) throws AssetInstanceNotFoundException {

    }
}
