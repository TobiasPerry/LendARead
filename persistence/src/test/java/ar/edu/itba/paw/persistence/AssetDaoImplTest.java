package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AssetDaoImplTest {
    @Autowired
    private DataSource ds;

    @Autowired
    private AssetDao assetDao;

    private final static String ISBN = "ISBN2";
    private final static String AUTHOR = "AUTHOR";
    private final static String TITLE = "TITLE";
    private final static String LANGUAGE = "LANGUAGE";
    private final static String ISBN_ALREADY_EXIST = "ISBN";
    private final static BookImpl book = new BookImpl(-1, ISBN, AUTHOR, TITLE, LANGUAGE);
    private final static BookImpl DUPLICATED_BOOK = new BookImpl(-1, ISBN_ALREADY_EXIST, AUTHOR, TITLE, LANGUAGE);


    @Rollback
    @Test
    public void addAssetTest() {
        //2
        final BookImpl bookReturned;
        try {
            bookReturned = assetDao.addAsset(book);
        } catch (BookAlreadyExistException e) {
            Assert.fail();
            return;
        }
        //3
        Assert.assertEquals(AUTHOR, bookReturned.getAuthor());
        Assert.assertEquals(ISBN, bookReturned.getIsbn());
        Assert.assertEquals(TITLE, bookReturned.getName());
        Assert.assertEquals(LANGUAGE, bookReturned.getLanguage());

    }

    @Rollback
    @Test
    public void addAssetTestDuplicated() {
        //3
        final BookImpl bookReturned;

        BookAlreadyExistException exception = Assert.assertThrows(BookAlreadyExistException.class, () -> {
            assetDao.addAsset(DUPLICATED_BOOK);
        });
    }

    @Rollback
    @Test
    public void getAssetTest() {
        //2
        Optional<BookImpl> returnBook = assetDao.getBook(ISBN_ALREADY_EXIST);
        //3
        Assert.assertTrue(returnBook.isPresent());
        Assert.assertEquals(DUPLICATED_BOOK.getName(),returnBook.get().getName());
        Assert.assertEquals( DUPLICATED_BOOK.getAuthor(),returnBook.get().getAuthor());
        Assert.assertEquals(DUPLICATED_BOOK.getLanguage(),returnBook.get().getLanguage());
    }

}
