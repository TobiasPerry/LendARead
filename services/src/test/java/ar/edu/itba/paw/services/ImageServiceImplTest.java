package ar.edu.itba.paw.services;

import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceImplTest {

    @InjectMocks
    ImageServiceImpl imageServiceImpl;
    @Mock
    ImagesDao imagesDao;

    private static final int ID = 1;



}
