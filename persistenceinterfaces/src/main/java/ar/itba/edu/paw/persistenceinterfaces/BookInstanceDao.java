package ar.itba.edu.paw.persistenceinterfaces;

import models.assetExistanceContext.interfaces.Book;

import java.util.List;
import java.util.Optional;

public interface BookInstanceDao {
    Optional<List<Book>> getAvailableBooks();
}
