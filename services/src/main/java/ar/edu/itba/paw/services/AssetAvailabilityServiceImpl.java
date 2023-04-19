package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

            sendBorrowerEmail(ai.get(), borrower);
            sendLenderEmail(ai.get(), borrower);
        }
        return saved;
    }

    private void sendLenderEmail(AssetInstance assetInstance, User borrower) {
        if (assetInstance == null || borrower == null) {
            return;
        }
        Map<String,Object> variables = new HashMap<>();
        User lender = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Book book = assetInstance.getBook();
        variables.put("bookTitle",book.getName());
        variables.put("name",lender.getName());
        variables.put("borrowerName",borrower.getName());
        variables.put("borroweEmail",borrower.getEmail());
        variables.put("province",location.getProvince());
        variables.put("locality",location.getLocality());
        variables.put("zipcode",location.getZipcode());
        variables.put("country",location.getCountry());
        String email = lender.getEmail();
        String bookName = book.getName();
        String subject = String.format("Lendabook: Préstamo de tu libro \"%s\"", bookName);
        emailService.sendEmail(email, subject, emailService.lenderMailFormat(variables,"lenderEmailTemplate.html"));
        System.out.println("SENT TO LENDER " + email);
    }

    private void sendBorrowerEmail(AssetInstance assetInstance, User borrower) {
        if (assetInstance == null || borrower == null) {
            return;
        }

        String email = borrower.getEmail();
        Book book = assetInstance.getBook();
        User owner = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Map<String,Object> variables = new HashMap<>();
        variables.put("bookTitle",book.getName());
        variables.put("name",borrower.getName());
        variables.put("LenderName",owner.getName());
        variables.put("LenderEmail",owner.getEmail());
        variables.put("province",location.getProvince());
        variables.put("locality",location.getLocality());
        variables.put("zipcode",location.getZipcode());
        variables.put("country",location.getCountry());

        emailService.sendEmail(email, "Lendabook: Préstamo libro " + book.getName(), emailService.lenderMailFormat(variables,"borrowerEmailTemplate.html"));
        System.out.println("SENT TO BORROWER " + email);
    }
}
