package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AssetAvailabilityServiceImpl implements AssetAvailabilityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetAvailabilityServiceImpl.class);
    private final AssetAvailabilityDao lendingDao;

    private final AssetInstanceDao assetInstanceDao;

    private final UserDao userDao;

    private final EmailService emailService;

    @Autowired
    public AssetAvailabilityServiceImpl(final AssetAvailabilityDao lendingDao, final AssetInstanceDao assetInstanceDao, final UserDao userDao, final EmailService emailService) {
        this.lendingDao = lendingDao;
        this.assetInstanceDao = assetInstanceDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public void borrowAsset(final int assetId, final String borrower, final LocalDate devolutionDate) throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException {
        Optional<AssetInstance> ai = assetInstanceDao.getAssetInstance(assetId);
        Optional<User> user = userDao.getUser(borrower);

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

        assetInstanceDao.changeStatus(assetId, AssetState.PENDING);
        int id = lendingDao.borrowAssetInstance(ai.get().getId(), user.get().getId(), LocalDate.now(), devolutionDate);
        emailService.sendBorrowerEmail(ai.get(), user.get(), id);
        emailService.sendLenderEmail(ai.get(), borrower, id);
        LOGGER.info("Asset {} has been borrow", assetId);
    }

    @Transactional
    @Override
    public void setAssetPrivate(final int assetId) throws AssetInstanceNotFoundException {

        if (!assetInstanceDao.changeStatus(assetId, AssetState.PRIVATE)) {
            LOGGER.error("Failed to update status to PRIVATE for asset instance with assetId: {}", assetId);
            throw new AssetInstanceNotFoundException("Asset instance not found with id: " + assetId);
        }
        LOGGER.info("Asset {} has been set private", assetId);
    }

    @Transactional
    @Override
    public void setAssetPublic(final int assetId) throws AssetInstanceNotFoundException {
        if (!assetInstanceDao.changeStatus(assetId, AssetState.PUBLIC)) {
            LOGGER.error("Failed to update status to PUBLIC for asset instance with assetId: {}", assetId);
            throw new AssetInstanceNotFoundException("Asset instance not found with id: " + assetId);
        }
        LOGGER.info("Asset {} has been set public", assetId);
    }

    @Transactional()
    @Override
    public void returnAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        if (!assetInstanceDao.changeStatusByLendingId(lendingId, AssetState.PRIVATE)) {
            LOGGER.error("Failed to update status to PRIVATE for asset instance with lendingId: {}", lendingId);
            throw new AssetInstanceNotFoundException("Asset instance not found for lendingId: " + lendingId);
        }
        if (!lendingDao.changeLendingStatus(lendingId, LendingState.FINISHED)) {
            LOGGER.error("Failed to update lending status to FINISHED for lending with lendingId: {}", lendingId);
            throw new LendingCompletionUnsuccessfulException("Failed to mark lending as finished for lendingId: " + lendingId);
        }
    }

    @Override
    public void confirmAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        if (!assetInstanceDao.changeStatusByLendingId(lendingId, AssetState.BORROWED)) {
            LOGGER.error("Failed to update status to BORROWED for asset instance with lendingId: {}", lendingId);
            throw new AssetInstanceNotFoundException("Asset instance not found for lendingId: " + lendingId);
        }
        if (!lendingDao.changeLendingStatus(lendingId, LendingState.DELIVERED)) {
            LOGGER.error("Failed to update lending status to DELIVERED for lending with lendingId: {}", lendingId);
            throw new LendingCompletionUnsuccessfulException("Failed to mark lending as delivered for lendingId: " + lendingId);
        }
    }

    @Override
    public void rejectAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        if (!assetInstanceDao.changeStatusByLendingId(lendingId, AssetState.PRIVATE)) {
            LOGGER.error("Failed to update status to PRIVATE for asset instance with lendingId: {}", lendingId);
            throw new AssetInstanceNotFoundException("Asset instance not found for lendingId: " + lendingId);
        }
        if (!lendingDao.changeLendingStatus(lendingId, LendingState.REJECTED)) {
            LOGGER.error("Failed to update lending status to REJECTED for lending with lendingId: {}", lendingId);
            throw new LendingCompletionUnsuccessfulException("Failed to mark lending as rejected for lendingId: " + lendingId);
        }
    }


}