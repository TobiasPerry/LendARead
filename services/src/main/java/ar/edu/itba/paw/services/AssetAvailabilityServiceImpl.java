package ar.edu.itba.paw.services;

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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AssetAvailabilityServiceImpl implements AssetAvailabilityService {
    private final AssetAvailabilityDao lendingDao;

    private final AssetInstanceDao assetInstanceDao;

    private final UserDao userDao;

    private final EmailService emailService;

    @Autowired
    public AssetAvailabilityServiceImpl(AssetAvailabilityDao lendingDao, AssetInstanceDao assetInstanceDao, UserDao userDao,EmailService emailService) {
        this.lendingDao = lendingDao;
        this.assetInstanceDao = assetInstanceDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @Override
    public boolean borrowAsset(int assetId, String borrower, LocalDate devolutionDate) {
        Optional<AssetInstance> ai = assetInstanceDao.getAssetInstance(assetId);
        Optional<User> user = userDao.getUser(borrower);
        if(!ai.isPresent() || !user.isPresent())
            return false;
        if(!ai.get().getAssetState().isPublic())
            return false;
        if (LocalDate.now().plusDays(ai.get().getMaxDays()).isBefore(devolutionDate) )
            return false;
        assetInstanceDao.changeStatus(assetId, AssetState.PENDING);
        boolean saved = lendingDao.borrowAssetInstance(ai.get().getId(),user.get().getId(),LocalDate.now(),devolutionDate);
        if (saved) {
            sendBorrowerEmail(ai.get(), user.get());
            sendLenderEmail(ai.get(), borrower);
        }
        return saved;
    }

    @Override
    public boolean setAssetPrivate(int assetId) {
        return assetInstanceDao.changeStatus(assetId, AssetState.PRIVATE);
    }

    @Override
    public boolean setAssetPublic(int assetId) {
        return assetInstanceDao.changeStatus(assetId, AssetState.PUBLIC);
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

    private void sendLenderEmail(AssetInstance assetInstance, String borrower) {
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
        String subject = String.format("Lendabook: Préstamo de tu libro %s", bookName);
        emailService.sendEmail(email, subject, emailService.lenderMailFormat(variables,"lenderEmailTemplate.html"));
    }

    private void sendBorrowerEmail(AssetInstance assetInstance, User borrower) {
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

        emailService.sendEmail(borrower.getEmail(), "Lendabook: Préstamo libro " + book.getName(), emailService.lenderMailFormat(variables,"borrowerEmailTemplate.html"));
    }
}
