package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AssetAvailabilityServiceImpl implements AssetAvailabilityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetAvailabilityServiceImpl.class);
    private final AssetAvailabilityDao lendingDao;

    private final AssetInstanceDao assetInstanceDao;

    private final UserDao userDao;

    private final EmailService emailService;

    private UserAssetsDao userAssetsDao;

    @Autowired
    public AssetAvailabilityServiceImpl(final AssetAvailabilityDao lendingDao, final AssetInstanceDao assetInstanceDao, final UserDao userDao, final EmailService emailService, final UserAssetsDao userAssetsDao) {
        this.lendingDao = lendingDao;
        this.assetInstanceDao = assetInstanceDao;
        this.userDao = userDao;
        this.emailService = emailService;
        this.userAssetsDao = userAssetsDao;
    }

    @Transactional
    @Override
    public void borrowAsset(final int assetId, final String borrower, final LocalDate devolutionDate) throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException {
        Optional<AssetInstanceImpl> ai = assetInstanceDao.getAssetInstance(assetId);
        Optional<UserImpl> user = userDao.getUser(borrower);

        if (!ai.isPresent()) {
            LOGGER.error("AssetInstance not found with id {}", assetId);
            throw new AssetInstanceBorrowException("The assetInstance or the user not found");
        }
        if (!user.isPresent()) {
            LOGGER.error("User not found: {}", borrower);
            throw new UserNotFoundException("The user not found");
        }
        if (!ai.get().getAssetState().isPublic()) {
            LOGGER.error("AssetInstance is not public with id {}", assetId);
            throw new AssetInstanceBorrowException("The assetInstance is not public");
        }
        if (LocalDate.now().plusDays(ai.get().getMaxDays()).isBefore(devolutionDate)) {
            LOGGER.error("Devolution date is out of range for asset with id {}", assetId);
            throw new DayOutOfRangeException();
        }
        LendingImpl lending = lendingDao.borrowAssetInstance(ai.get(), user.get(), LocalDate.now(), devolutionDate, LendingState.ACTIVE);
        emailService.sendBorrowerEmail(ai.get(), user.get(), lending.getId(), LocaleContextHolder.getLocale());
        emailService.sendLenderEmail(ai.get(), borrower, lending.getId(), LocaleContextHolder.getLocale());
        LOGGER.info("Asset {} has been borrow", assetId);
    }

    @Transactional
    @Override
    public void setAssetPrivate(final int assetId) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = assetInstanceDao.getAssetInstance(assetId).orElseThrow(() -> new AssetInstanceNotFoundException("Asset instance not found with id: " + assetId));
        assetInstanceDao.changeStatus(assetInstance, AssetState.PRIVATE);
        LOGGER.info("Asset {} has been set private", assetId);
    }

    @Transactional
    @Override
    public void setAssetPublic(final int assetId) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = assetInstanceDao.getAssetInstance(assetId).orElseThrow(() -> new AssetInstanceNotFoundException("Asset instance not found with id: " + assetId));
        assetInstanceDao.changeStatus(assetInstance, AssetState.PUBLIC);
        LOGGER.info("Asset {} has been set public", assetId);
    }

    @Transactional()
    @Override
    public void returnAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        LendingImpl lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(() -> new LendingCompletionUnsuccessfulException("Lending not found for lendingId: " + lendingId));
        assetInstanceDao.changeStatus(lending.getAssetInstance(), AssetState.PRIVATE);
        lendingDao.changeLendingStatus(lending, LendingState.FINISHED);
        emailService.sendReviewBorrower(lending.getAssetInstance(), lending.getUserReference(), lending.getAssetInstance().getOwner(), lending.getId(), LocaleContextHolder.getLocale());
        emailService.sendReviewLender(lending.getAssetInstance(), lending.getAssetInstance().getOwner(), lending.getUserReference(), lending.getId(), LocaleContextHolder.getLocale());
    }

    @Transactional
    @Override
    public void confirmAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        LendingImpl lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(() -> new LendingCompletionUnsuccessfulException("Lending not found for lendingId: " + lendingId));
        assetInstanceDao.changeStatus(lending.getAssetInstance(), AssetState.BORROWED);
        lendingDao.changeLendingStatus(lending, LendingState.DELIVERED);
    }

    @Transactional
    @Override
    public void rejectAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        LendingImpl lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(() -> new LendingCompletionUnsuccessfulException("Lending not found for lendingId: " + lendingId));
        assetInstanceDao.changeStatus(lending.getAssetInstance(), AssetState.PRIVATE);
        lendingDao.changeLendingStatus(lending, LendingState.REJECTED);
        emailService.sendRejectedEmail(lending.getAssetInstance(), lending.getUserReference(), lending.getId(), LocaleContextHolder.getLocale());
    }
    @Transactional(readOnly = true)
    @Override
    public List<LendingImpl> getActiveLendings(final AssetInstanceImpl ai) {
        return lendingDao.getActiveLendings(ai);
    }

}