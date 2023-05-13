package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final static Book book = new BookImpl(-1,ISBN,AUTHOR,TITLE,LANGUAGE);
    private final static Book DUPLICATED_BOOK = new BookImpl(-1,ISBN_ALREADY_EXIST,AUTHOR,TITLE,LANGUAGE);



    @Rollback
    @Test
    public void addAssetTest(){
        //2
        final Book bookReturned;
        try {
            bookReturned = assetDao.addAsset(book);
        } catch (BookAlreadyExistException e) {
            Assert.fail();
            return;
        }
        //3
        Assert.assertEquals(AUTHOR,bookReturned.getAuthor());
        Assert.assertEquals(ISBN,bookReturned.getIsbn());
        Assert.assertEquals(TITLE,bookReturned.getName());
        Assert.assertEquals(LANGUAGE,bookReturned.getLanguage());

    }
    @Rollback
    @Test
    public void addAssetTestDuplicated(){
        //3
        final Book bookReturned;

        BookAlreadyExistException exception = Assert.assertThrows(BookAlreadyExistException.class, () -> {
            assetDao.addAsset(DUPLICATED_BOOK);
        });
    }
    @Rollback
    @Test
    public void getAssetTest(){
        //2
        Optional<Book> returnBook = assetDao.getBook(ISBN_ALREADY_EXIST);
        //3
        Assert.assertTrue(returnBook.isPresent());
        Assert.assertEquals(returnBook.get().getName(),DUPLICATED_BOOK.getName());
        Assert.assertEquals(returnBook.get().getAuthor(),DUPLICATED_BOOK.getAuthor());
        Assert.assertEquals(returnBook.get().getLanguage(),DUPLICATED_BOOK.getLanguage());
    }

}
