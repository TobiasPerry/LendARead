package ar.edu.itba.paw.services;


import ar.edu.itba.paw.exceptions.AssetAlreadyExistException;
import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToCreateAssetException;
import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.edu.itba.paw.models.assetExistanceContext.Language;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssetImplTest {


    @InjectMocks
    private AssetServiceImpl assetService;

    @Mock
    private AssetDao assetDao;

    @Mock
    private LanguagesService languagesService;

    @Test(expected = AssetAlreadyExistException.class)
    public void addBookAlreadyExistTest() throws AssetAlreadyExistException, UnableToCreateAssetException, LanguageNotFoundException, BookAlreadyExistException {
        // 1 - Precondiciones
        when(languagesService.getLanguage(any())).thenReturn(Optional.of(new Language()));
        when(assetDao.addAsset(any())).thenThrow(new BookAlreadyExistException());

        // 2 - Ejercitación
        assetService.addAsset("", "", "", "");

        // 3 - Assertions
        Assert.fail();
    }
    @Test(expected = UnableToCreateAssetException.class)
    public void addBookLanguageNotFoundTest() throws AssetAlreadyExistException, UnableToCreateAssetException, LanguageNotFoundException, BookAlreadyExistException {
        // 1 - Precondiciones
        when(languagesService.getLanguage(any())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        assetService.addAsset("", "", "", "");

        // 3 - Assertions
        Assert.fail();
    }
    @Test()
    public void getBookByIdNotFoundTest()  {
        // 1 - Precondiciones
        when(assetDao.getBookById(any())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        Assert.assertEquals(Optional.empty(),assetService.getAssetById(0L));

    }


}
