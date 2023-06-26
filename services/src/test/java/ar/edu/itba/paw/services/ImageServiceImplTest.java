package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @InjectMocks
    ImageServiceImpl imageServiceImpl;
    @Mock
    ImagesDao imagesDao;

    private static final int ID = 1;

    @Test(expected = ImageNotFoundException.class)
    public void getPhotoTest() throws ImageNotFoundException {
        // 1 - Precondiciones
        when(imagesDao.getPhoto(anyInt())).thenReturn(Optional.empty());

        // 2 - Ejercitación
        imageServiceImpl.getPhoto(ID);

        // 3 - Assertions
        Assert.fail();
    }

}
