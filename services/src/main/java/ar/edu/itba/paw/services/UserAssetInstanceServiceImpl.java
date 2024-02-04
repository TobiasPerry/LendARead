package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserAssetInstanceServiceImpl implements UserAssetInstanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAssetInstanceService.class);
    private final UserAssetsDao userAssetsDao;


    @Autowired
    public UserAssetInstanceServiceImpl(final UserAssetsDao userAssetsDao) {
        this.userAssetsDao = userAssetsDao;
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Lending> getBorrowedAssetInstance(final int lendingId) {
      return userAssetsDao.getBorrowedAsset(lendingId);

    }

}
