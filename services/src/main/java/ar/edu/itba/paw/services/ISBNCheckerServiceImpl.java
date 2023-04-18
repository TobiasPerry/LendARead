package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ISBNCheckerService;
import ar.edu.itba.paw.interfaces.LibraryAPIService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

@Service
public class ISBNCheckerServiceImpl implements ISBNCheckerService {

    @Autowired
    LibraryAPIService libraryAPIService;

    @Autowired
    AssetDao assetDao;

    @Override
    public Optional<Book> getBookIfExistsByISBN(String isbn) {
        Optional<Book> bookOpt = this.assetDao.getBook(isbn);
        if (bookOpt.isPresent()) {
            return bookOpt;
        }
        try {
            Book book = this.libraryAPIService.getBookByISBN(isbn);
            return Optional.of(book);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
