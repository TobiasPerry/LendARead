package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    public List<? extends AssetInstance> getUserAssetsOfTable(final String email, final String tableSelected, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction) {

        switch (tableSelected) {
            case "my_books":
                return userAssetsDao.getUsersAssets(email, filterAtribuite, filterValue, sortAtribuite, direction);
            case "borrowed_books":
                return userAssetsDao.getBorrowedAssets(email, filterAtribuite, filterValue, sortAtribuite, direction);
            case "lended_books":
                return userAssetsDao.getLendedAssets(email, filterAtribuite, filterValue, sortAtribuite, direction);
        }

        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    @Override
    public BorrowedAssetInstance getBorrowedAssetInstance(final int lendingId) throws AssetInstanceNotFoundException {
      Optional<BorrowedAssetInstance> borrowedAssetInstance = userAssetsDao.getBorrowedAsset(lendingId);
      if (!borrowedAssetInstance.isPresent()) {
          LOGGER.error("Not found borrowed asset instance with the lending lendingId: {}", lendingId);
          throw new AssetInstanceNotFoundException("Not found BorrowedAssetInstance");
      }
      return borrowedAssetInstance.get();
    }

}
