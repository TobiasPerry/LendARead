package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.implementations.UserAssetsImpl;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserAssetInstanceServiceImpl implements UserAssetInstanceService {

    private final UserAssetsDao   userAssetsDao;


    @Autowired
    public UserAssetInstanceServiceImpl(final UserAssetsDao userAssetsDao) {
        this.userAssetsDao = userAssetsDao;
    }

    @Override
    public UserAssets getUserAssets(final String email, final String tableSelected, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction) {
        return new UserAssetsImpl(
                userAssetsDao.getLendedAssets(email,  filterAtribuite, matchFilterValue(filterValue),  matchSortAttribuite(sortAtribuite), direction),
                userAssetsDao.getBorrowedAssets(email, filterAtribuite, matchFilterValue(filterValue),  sortAtribuite, direction),
                userAssetsDao.getUsersAssets(email,  filterAtribuite, matchFilterValue(filterValue) , sortAtribuite, direction)
        );
    }

    private String matchFilterValue(String filterAtribuite) {
        return filterAtribuite.toUpperCase();
    }

    private String matchSortAttribuite(String sortAtribuite) {
        if(sortAtribuite.equals("book_name")) return "b.title";
        if(sortAtribuite.equals("expected_retrieval_date")) return "l.devolutiondate";
        if(sortAtribuite.equals("borrower_name")) return "u.name";
        return sortAtribuite;
    }


}
