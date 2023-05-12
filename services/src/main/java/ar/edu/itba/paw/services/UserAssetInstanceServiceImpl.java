package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAssetInstanceServiceImpl implements UserAssetInstanceService {

    private final UserAssetsDao   userAssetsDao;


    @Autowired
    public UserAssetInstanceServiceImpl(final UserAssetsDao userAssetsDao) {
        this.userAssetsDao = userAssetsDao;
    }

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

}
