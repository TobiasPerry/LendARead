package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.LendingService;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.assetLendingContext.LendingState;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class LendingServiceImpl implements LendingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LendingServiceImpl.class);
    private final AssetAvailabilityDao lendingDao;

    private final AssetInstanceDao assetInstanceDao;

    private final UserDao userDao;

    private final EmailService emailService;

    private final UserAssetsDao userAssetsDao;


    @Autowired
    public LendingServiceImpl(final AssetAvailabilityDao lendingDao, final AssetInstanceDao assetInstanceDao, final UserDao userDao, final EmailService emailService, final UserAssetsDao userAssetsDao) {
        this.lendingDao = lendingDao;
        this.assetInstanceDao = assetInstanceDao;
        this.userDao = userDao;
        this.emailService = emailService;
        this.userAssetsDao = userAssetsDao;
    }

    @Transactional
    @Override
    public Lending borrowAsset(final int assetId, final String borrower, final LocalDate borrowDate, final LocalDate devolutionDate) throws AssetInstanceBorrowException, DayOutOfRangeException, MaxLendingDaysException, AssetIsNotAvailableException, AssetInstanceIsNotReservableException {
        Optional<AssetInstance> ai = assetInstanceDao.getAssetInstance(assetId);
        Optional<User> user = userDao.getUser(borrower);

        if (!ai.isPresent()) {
            LOGGER.error("AssetInstance not found with id {}", assetId);
            throw new AssetInstanceBorrowException();
        }
        if (!user.isPresent()) {
            LOGGER.error("User not found: {}", borrower);
            throw new AssetIsNotAvailableException();
        }
        if (!ai.get().getAssetState().isPublic()) {
            LOGGER.error("AssetInstance is not public with id {}", assetId);
            throw new AssetIsNotAvailableException();
        }
        if (!ai.get().getIsReservable() && !borrowDate.isEqual(LocalDate.now())) {
            LOGGER.error("AssetInstance is not reservable with id {}", assetId);
            throw new AssetInstanceIsNotReservableException();
        }
        if (borrowDate.plusDays(ai.get().getMaxDays()).isBefore(devolutionDate)) {
            LOGGER.error("Devolution date is out of range for asset with id {}", assetId);
            throw new MaxLendingDaysException();
        }

        List<Lending> lendings = lendingDao.getActiveLendings(ai.get());
        if (checkOverlapping(borrowDate, devolutionDate, lendings)) {
            LOGGER.error("AssetInstance is not available with id {}", assetId);
            throw new DayOutOfRangeException();
        }
        if (!ai.get().getIsReservable()) {
            ai.get().setAssetState(AssetState.PRIVATE);
        }
        Lending lending = lendingDao.borrowAssetInstance(ai.get(), user.get(), borrowDate, devolutionDate, LendingState.ACTIVE);
        emailService.sendBorrowerEmail(ai.get(), user.get(), lending.getId(), new Locale(user.get().getLocale()));
        emailService.sendLenderEmail(ai.get(), borrower, lending.getId(), new Locale(ai.get().getOwner().getLocale()));
        LOGGER.info("Asset {} has been borrow", assetId);
        return lending;
    }

    public static boolean checkOverlapping(LocalDate fechaInicial, LocalDate fechaFinal, List<Lending> lendings) {
        for (Lending lending : lendings) {
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
    public void returnAsset(final int lendingId) throws InvalidLendingStateTransitionException, LendingNotFoundException {
        Lending lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(LendingNotFoundException::new);

        if (lending.getActive() != LendingState.DELIVERED)
            throw new InvalidLendingStateTransitionException();
        if (!lending.getAssetInstance().getIsReservable())
            assetInstanceDao.changeStatus(lending.getAssetInstance(), AssetState.PRIVATE);
        lendingDao.changeLendingStatus(lending, LendingState.FINISHED);
        emailService.sendReviewBorrower(lending.getAssetInstance(), lending.getUserReference(), lending.getAssetInstance().getOwner(), lending.getId(), new Locale(lending.getUserReference().getLocale()));
        emailService.sendReviewLender(lending.getAssetInstance(), lending.getAssetInstance().getOwner(), lending.getUserReference(), lending.getId(), new Locale(lending.getAssetInstance().getOwner().getLocale()));
    }

    @Transactional
    @Override
    public void confirmAsset(final int lendingId) throws InvalidLendingStateTransitionException, LendingNotFoundException {
        Lending lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(LendingNotFoundException::new);

        if (lending.getActive() != LendingState.ACTIVE || userAssetsDao.getActiveLendingsCount(lending.getAssetInstance().getId()) >= 1 )
            throw new InvalidLendingStateTransitionException();
        lendingDao.changeLendingStatus(lending, LendingState.DELIVERED);
    }

    @Transactional(readOnly = true)
    @Override
    public User getLender(int lendingId) throws LendingNotFoundException {
        return lendingDao.getLendingById(lendingId).orElseThrow(LendingNotFoundException::new).getAssetInstance().getOwner();
    }

    @Transactional
    @Override
    public void rejectAsset(final int lendingId) throws InvalidLendingStateTransitionException, LendingNotFoundException {
        Lending lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(LendingNotFoundException::new);

        if (lending.getActive() != LendingState.ACTIVE) {
            throw new InvalidLendingStateTransitionException();
        }
        lendingDao.changeLendingStatus(lending, LendingState.REJECTED);
        emailService.sendRejectedEmail(lending.getAssetInstance(), lending.getUserReference(), lending.getId(), new Locale(lending.getUserReference().getLocale()));
    }

    @Transactional
    @Override
    public void changeLending(final int lendingId, final String state) throws InvalidLendingStateTransitionException, LendingNotFoundException {
       switch (state) {
           case "DELIVERED":
               confirmAsset(lendingId);
               break;
           case "REJECTED":
               rejectAsset(lendingId);
               break;
           case "FINISHED":
               returnAsset(lendingId);
               break;
           case "CANCEL":
                cancelAsset(lendingId);
                break;
           default:
               throw new InvalidLendingStateTransitionException();
       }
    }


    @Transactional(readOnly = true)
    @Override
    public PagingImpl<Lending> getPagingActiveLendings(final int page, final int size, final Integer aiId, final Integer borrowerId, final List<String> lendingState, final Integer lenderId, final String sort, final String sortDirection, final LocalDate startingBefore, final LocalDate startingAfter, final LocalDate endBefore, final LocalDate endAfter) {
        return lendingDao.getPagingActiveLending(page, size, aiId, borrowerId, lendingState, lenderId, sort, sortDirection, startingBefore, startingAfter, endBefore, endAfter);
    }

    @Transactional(readOnly = true)
    @Override
    public User getBorrower(int lendingId) throws LendingNotFoundException {
        return lendingDao.getLendingById(lendingId).orElseThrow(LendingNotFoundException::new).getUserReference();
    }

    @Transactional
    @Override
    public void cancelAsset(int lendingId) throws InvalidLendingStateTransitionException, LendingNotFoundException {
        Lending lending = userAssetsDao.getBorrowedAsset(lendingId).orElseThrow(LendingNotFoundException::new);

        if (lending.getActive() != LendingState.ACTIVE) {
            throw new InvalidLendingStateTransitionException();
        }
        lendingDao.changeLendingStatus(lending, LendingState.CANCELED);
        emailService.sendCanceledEmail(lending.getAssetInstance(), lending.getId(), new Locale(lending.getAssetInstance().getOwner().getLocale()));
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Async
    @Override
    public void notifyNewLendings() {
        Optional<List<Lending>> maybeNewLendingList = lendingDao.getActiveLendingsStartingOn(LocalDate.now());
        if (maybeNewLendingList.isPresent()) {
            for (Lending lending : maybeNewLendingList.get()) {
                emailService.sendRemindLendingToLender(lending, lending.getAssetInstance().getOwner(), lending.getUserReference(), new Locale(lending.getAssetInstance().getOwner().getLocale()));
            }
        }
        Optional<List<Lending>> maybeReturnLendingList = lendingDao.getActiveLendingEndingOn(LocalDate.now());
        if (maybeReturnLendingList.isPresent()) {
            for (Lending lending : maybeReturnLendingList.get()) {
                emailService.sendRemindReturnToLender(lending, lending.getAssetInstance().getOwner(), lending.getUserReference(), new Locale(lending.getAssetInstance().getOwner().getLocale()));
            }
        }
    }

}