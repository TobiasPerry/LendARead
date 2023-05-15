package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class LibraryAPIServiceImplTest {


    @InjectMocks
    private LibraryAPIServiceImpl libraryAPIService;



    //   The following book is present in the API's library:
    private static final String BOOK_ISBN_VALID = "9780321125217";
    private static final String BOOK_TITLE_VALID = "Domain-driven design";
    private static final String BOOK_AUTHOR_VALID = "Eric Evans";
    private static final String BOOK_LANGUAGE_VALID = "English";

    //   The following ISBN is not valid:
    private static final String BOOK_ISBN_INVALID = "9780325217";

    @Test
    public void validISBNTest() throws IOException {
        // 1 - Preconditions

        // 2 - Ejercitacion
        Book book = libraryAPIService.getBookByISBN(BOOK_ISBN_VALID);

        // 3 - Assertions
        Assert.assertEquals(book.getName(), BOOK_TITLE_VALID);
        Assert.assertEquals(book.getAuthor(), BOOK_AUTHOR_VALID);
        Assert.assertEquals(book.getLanguage(), BOOK_LANGUAGE_VALID);
    }

    @Test(expected = IOException.class)
    public void invalidISBNTest() throws IOException{
        // 1 - Preconditions

        // 2 - Ejercitación
        Book book = libraryAPIService.getBookByISBN(BOOK_ISBN_INVALID);

        // 3 - Assertions
        Assert.fail();
    }

}
