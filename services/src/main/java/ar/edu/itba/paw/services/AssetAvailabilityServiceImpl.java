package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@EnableScheduling
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
    public void borrowAsset(final int assetId, final String borrower, final  LocalDate borrowDate,final LocalDate devolutionDate) throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException {
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
        if (borrowDate.plusDays(ai.get().getMaxDays()).isBefore(devolutionDate)) {
            LOGGER.error("Devolution date is out of range for asset with id {}", assetId);
            throw new DayOutOfRangeException();
        }
        if(!ai.get().getIsReservable() && !borrowDate.isEqual(LocalDate.now())){
            LOGGER.error("AssetInstance is not reservable with id {}", assetId);
            throw new AssetInstanceBorrowException("The assetInstance is not reservable");
        }

        if (!ai.get().getIsReservable()){
            ai.get().setAssetState(AssetState.PRIVATE);
        }else{
            List<LendingImpl> lending = lendingDao.getActiveLendings(ai.get());
            if (verificarSolapamiento(borrowDate, devolutionDate, lending)) {
                LOGGER.error("AssetInstance is not available with id {}", assetId);
                throw new AssetInstanceBorrowException("The assetInstance is not available");
            }
        }
        LendingImpl lending = lendingDao.borrowAssetInstance(ai.get(), user.get(), borrowDate, devolutionDate, LendingState.ACTIVE);
        emailService.sendBorrowerEmail(ai.get(), user.get(), lending.getId(), new Locale(user.get().getLocale()));
        emailService.sendLenderEmail(ai.get(), borrower, lending.getId(),new Locale(ai.get().getOwner().getLocale()));
        LOGGER.info("Asset {} has been borrow", assetId);
    }

    public static boolean verificarSolapamiento(LocalDate fechaInicial, LocalDate fechaFinal, List<LendingImpl> lendings) {
        for (LendingImpl lending : lendings) {
            LocalDate borrowDate = lending.getLendDate();
            LocalDate devolutionDate = lending.getDevolutionDate();
            if ((borrowDate.isAfter(fechaInicial) || borrowDate.isEqual(fechaInicial))
                    && (borrowDate.isBefore(fechaFinal) || borrowDate.isEqual(fechaFinal))) {
                return true;
            }
            if ((devolutionDate.isAfter(fechaInicial) || devolutionDate.isEqual(fechaInicial))
                    && (devolutionDate.isBefore(fechaFinal) || devolutionDate.isEqual(fechaFinal))) {
                return true;
            }
            if ((borrowDate.isBefore(fechaInicial) || borrowDate.isEqual(fechaInicial))
                    && (devolutionDate.isAfter(fechaFinal) || devolutionDate.isEqual(fechaFinal))) {
                return true;
            }
        }
        return false;
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
    public void changeReservability(int assetId) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = assetInstanceDao.getAssetInstance(assetId).orElseThrow(() -> new AssetInstanceNotFoundException("Asset instance not found with id: " + assetId));
        assetInstanceDao.setReservability(assetInstance, !assetInstance.getIsReservable());
        LOGGER.info("Asset {} has been chaned its reservability", assetId);
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
        if(!lending.getAssetInstance().getIsReservable())
            assetInstanceDao.changeStatus(lending.getAssetInstance(), AssetState.PRIVATE);
        lendingDao.changeLendingStatus(lending, LendingState.FINISHED);
        emailService.sendReviewBorrower(lending.getAssetInstance(), lending.getUserReference(), lending.getAssetInstance().getOwner(), lending.getId(), new Locale(lending.getUserReference().getLocale()));
        emailService.sendReviewLender(lending.getAssetInstance(), lending.getAssetInstance().getOwner(), lending.getUserReference(), lending.getId(), new Locale(lending.getAssetInstance().getOwner().getLocale()));
    }

    @Transactional
    @Override
    public void confirmAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        LendingImpl lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(() -> new LendingCompletionUnsuccessfulException("Lending not found for lendingId: " + lendingId));
        lendingDao.changeLendingStatus(lending, LendingState.DELIVERED);
    }

    @Transactional
    @Override
    public void rejectAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        LendingImpl lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(() -> new LendingCompletionUnsuccessfulException("Lending not found for lendingId: " + lendingId));
        lendingDao.changeLendingStatus(lending, LendingState.REJECTED);
        emailService.sendRejectedEmail(lending.getAssetInstance(), lending.getUserReference(), lending.getId(), new Locale(lending.getUserReference().getLocale()));
    }
    @Transactional(readOnly = true)
    @Override
    public List<LendingImpl> getActiveLendings(final AssetInstanceImpl ai) {
        return lendingDao.getActiveLendings(ai);
    }

    @Transactional(readOnly = true)
    @Override
    public PagingImpl<LendingImpl> getPagingActiveLendings(final AssetInstanceImpl ai, final int page, final int size) {
        return lendingDao.getPagingActiveLending(ai, page, size);
    }
    @Transactional
    @Override
    public void cancelAsset(int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        LendingImpl lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(() -> new LendingCompletionUnsuccessfulException("Lending not found for lendingId: " + lendingId));
        lendingDao.changeLendingStatus(lending, LendingState.REJECTED);
        emailService.sendCanceledEmail(lending.getAssetInstance(), lending.getId(),new Locale(lending.getAssetInstance().getOwner().getLocale()));
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Async
    @Override
    public void notifyNewLendings() {
        Optional<List<LendingImpl>> maybeNewLendingList = lendingDao.getActiveLendingsStartingOn(LocalDate.now());
        if (maybeNewLendingList.isPresent()) {
            for (LendingImpl lending : maybeNewLendingList.get()) {
                emailService.sendRemindLendingToLender(lending, lending.getAssetInstance().getOwner(), lending.getUserReference(), new Locale(lending.getAssetInstance().getOwner().getLocale()));
            }
        }

        Optional<List<LendingImpl>> maybeReturnLendingList = lendingDao.getActiveLendingEndingOn(LocalDate.now());
        if (maybeReturnLendingList.isPresent()) {
            for (LendingImpl lending : maybeReturnLendingList.get()) {
                emailService.sendRemindReturnToLender(lending, lending.getAssetInstance().getOwner(), lending.getUserReference(), new Locale(lending.getAssetInstance().getOwner().getLocale()));
            }
        }
    }

}