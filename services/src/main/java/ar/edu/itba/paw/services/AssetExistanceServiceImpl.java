package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.*;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
final public class AssetExistanceServiceImpl implements AssetExistanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    private final AssetDao bookDao;

    private final UserDao userDao;

    private final AssetInstanceDao assetInstanceDao;

    private final LocationDao locationDao;

    private final ImagesDao photosDao;

    @Autowired
    public AssetExistanceServiceImpl(AssetDao bookDao, UserDao userDao, AssetInstanceDao assetInstanceDao, LocationDao locationDao, ImagesDao photosDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.assetInstanceDao = assetInstanceDao;
        this.locationDao = locationDao;
        this.photosDao = photosDao;
    }

    @Override
    @Transactional
    public AssetInstance addAssetInstance(AssetInstance assetInstance, byte[] photo) throws InternalErrorException {

        Optional<Book> bookOptional = bookDao.getBook(assetInstance.getBook().getIsbn());
        Book book;
        if (!bookOptional.isPresent()) {
            try {
                book = bookDao.addAsset(assetInstance.getBook());
            }catch (BookAlreadyExistException exception){
                LOGGER.error("Failed to add  a new book");
                throw new InternalErrorException("Internal error");
            }
        }else {
            book = bookOptional.get();
        }
        Optional<User> user = userDao.getUser(assetInstance.getOwner().getEmail());
        Location locationId = locationDao.addLocation(assetInstance.getLocation());
        Optional<Integer> photoId =  photosDao.addPhoto(photo);

        if( user.isPresent()  && photoId.isPresent()) {
            LOGGER.info("Add a new asset intance");
            return assetInstanceDao.addAssetInstance(book, user.get(),locationId,photoId.get(),assetInstance);
        } else {
            LOGGER.error("Failed to add  a new book instance");
            throw new InternalErrorException("Cannot addAssetInstance");
        }
    }
}
