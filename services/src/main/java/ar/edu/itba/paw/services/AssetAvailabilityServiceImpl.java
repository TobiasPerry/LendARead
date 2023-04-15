package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
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
            sendBorrowerEmail(ai.get(), borrower);
            sendLenderEmail(ai.get(), borrower);
        }
        return saved;
    }

    private void sendLenderEmail(AssetInstance assetInstance, User borrower) {
        if (assetInstance == null || borrower == null) {
            return;
        }

        User lender = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Book book = assetInstance.getBook();

        String email = lender.getEmail();
        String lenderName = lender.getName();
        String borrowerName = borrower.getName();
        String bookName = book.getName();
        String borrowerEmail = borrower.getEmail();
        String locationCountry = location.getCountry();
        String locationProvince = location.getProvince();
        String locationName = location.getLocality();
        String locationZip = location.getZipcode();

        String subject = String.format("Lendabook: Préstamo de tu libro \"%s\"", bookName);
        String body = String.format("<p>Hola %s</p>\n" +
                "<p>Te informamos que tu libro &quot;%s&quot; ha sido solicitado para su préstamo. \n" +
                "Los datos del usuario a prestar el libro son los siguientes:</p>\n" +
                "<ul>\n" +
                "<li>Nombre: %s</li>\n" +
                "<li>Email: %s</li>\n" +
                "</ul>\n" +
                "<p>Te recordamos que los detalles suministrados de la ubicación para la entrega del mismo son los siguientes:</p>\n" +
                "<ul>\n" +
                "<li>País: %s</li>\n" +
                "<li>Provincia: %s</li>\n" +
                "<li>Localidad: %s</li>\n" +
                "<li>Código Postal: %s</li>\n" +
                "</ul>\n" +
                "<p>Por favor, mantente en contacto con quien solicitó el préstamo para acordar algún otro detalle.\n" +
                "Muchas Gracias por utilizar LendABook! \n" +
                "Puedes seguir explorando libros ingresando <a href=\"http://localhost:8080/\">aquí</a></p>\n",
                lenderName, bookName, borrowerName, borrowerEmail, locationCountry, locationProvince, locationName, locationZip);

        EmailServiceImpl.sendEmail(email, subject, body);
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

        String bookName = book.getName();
        String borrowerName = borrower.getName();
        String ownerEmail = owner.getEmail();
        String ownerName = owner.getName();
        String locationName = location.getLocality();
        String locationProvince = location.getProvince();
        String locationCountry = location.getCountry();
        String locationZip = location.getZipcode();

        StringBuilder subjectBuilder = new StringBuilder();
        subjectBuilder.append("Lendabook: Préstamo libro ").append(bookName);

        String body = String.format("<p>Hola %s</p>\n" +
                "<p>Te informamos que se ha confirmado el préstamo del libro &quot;%s&quot;.\n" +
                "Te presentamos la información de contacto de su dueño:</p>\n" +
                "<ul>\n" +
                "<li>Nombre: %s</li>\n" +
                "<li>Email: %s</li>\n" +
                "</ul>\n" +
                "<p>Los detalles de la ubicación en la cual se deberá realizar la entrega son los siguientes:</p>\n" +
                "<ul>\n" +
                "<li>País: %s</li>\n" +
                "<li>Provincia: %s</li>\n" +
                "<li>Localidad: %s</li>\n" +
                "<li>Código Postal: %s</li>\n" +
                "</ul>\n" +
                "<p>Por favor, mantente en contacto con el dueño del libro para acordar algún otro detalle.</p>\n" +
                "<p>Muchas Gracias por utilizar LendABook!\n" +
                "Puedes seguir explorando libros ingresando <a href=\"http://localhost:8080\">aquí</a></p>\n", borrowerName, bookName, ownerName, ownerEmail, locationCountry, locationName, locationProvince, locationZip);

        EmailServiceImpl.sendEmail(email, subjectBuilder.toString(), body);
        System.out.println("SENT TO BORROWER " + email);
    }
}
