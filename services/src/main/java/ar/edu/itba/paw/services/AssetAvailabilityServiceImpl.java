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

import java.time.LocalDate;
import java.util.*;

@Service
public class AssetAvailabilityServiceImpl implements AssetAvailabilityService {
    private final AssetAvailabilityDao lendingDao;

    private final AssetInstanceDao assetInstanceDao;

    private final UserDao userDao;

    private final EmailService emailService;
    private final MessageSource messageSource;

    @Autowired
    public AssetAvailabilityServiceImpl(final AssetAvailabilityDao lendingDao,final AssetInstanceDao assetInstanceDao,final UserDao userDao,final EmailService emailService,final MessageSource messageSource) {
        this.lendingDao = lendingDao;
        this.assetInstanceDao = assetInstanceDao;
        this.userDao = userDao;
        this.emailService = emailService;
        this.messageSource = messageSource;
    }

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


        assetInstanceDao.changeStatus(assetId, AssetState.BORROWED);
        boolean saved = lendingDao.borrowAssetInstance(ai.get().getId(),user.get().getId(),LocalDate.now(),devolutionDate);
        if (saved) {
            sendBorrowerEmail(ai.get(), user.get());
            sendLenderEmail(ai.get(), borrower);
        }else{
            throw new AssetInstanceBorrowException("Asset cant be lending");
        }
    }

    @Override
    public void setAssetPrivate(final int assetId) throws AssetInstanceNotFoundException {
        if(!assetInstanceDao.changeStatus(assetId, AssetState.PRIVATE))
            throw new AssetInstanceNotFoundException("Asset instance not found");
    }

    @Override
    public void setAssetPublic(final int assetId) throws AssetInstanceNotFoundException {
        if(!assetInstanceDao.changeStatus(assetId, AssetState.PUBLIC))
            throw new AssetInstanceNotFoundException("Asset instance not found");
    }

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

    private void sendLenderEmail(final AssetInstance assetInstance,final String borrower) {
        if (assetInstance == null || borrower == null) {
            return;
        }
        Map<String,Object> variables = new HashMap<>();
        User owner = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Book book = assetInstance.getBook();
        variables.put("book",book);
        variables.put("borrower",borrower);
        variables.put("owner",owner);
        variables.put("location",location);
        String email = owner.getEmail();
        String bookName = book.getName();
        String subject = String.format(messageSource.getMessage("email.lender.subject",null, LocaleContextHolder.getLocale()), bookName);
        emailService.sendEmail(email, subject, emailService.lenderMailFormat(variables,"lenderEmailTemplate.html"));
    }

    private void sendBorrowerEmail(final AssetInstance assetInstance,final User borrower) {
        if (assetInstance == null || borrower == null) {
            return;
        }

        Book book = assetInstance.getBook();
        User owner = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Map<String,Object> variables = new HashMap<>();
        variables.put("book",book);
        variables.put("borrower",borrower.getName());
        variables.put("owner",owner);
        variables.put("location",location);
        String subject = String.format(messageSource.getMessage("email.borrower.subject",null, LocaleContextHolder.getLocale()), assetInstance.getBook().getName());

        emailService.sendEmail(borrower.getEmail(), subject, emailService.lenderMailFormat(variables,"borrowerEmailTemplate.html"));
    }
}
