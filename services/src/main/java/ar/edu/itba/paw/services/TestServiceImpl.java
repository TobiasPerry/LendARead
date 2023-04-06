package ar.edu.itba.paw.services;

import ar.itba.edu.paw.persistenceinterfaces.BookInstanceDao;
import interfaces.TestService;
import models.assetExistanceContext.interfaces.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private BookInstanceDao bookDao;
    @Override
    public void createAsset(Book bi) {
        bookDao.addAssetInstance(bi);
    }
}
