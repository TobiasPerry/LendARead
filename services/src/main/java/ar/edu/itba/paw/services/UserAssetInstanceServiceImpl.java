package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.userContext.implementations.UserAssetsImpl;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAssetInstanceServiceImpl implements UserAssetInstanceService {

    private final AssetInstanceService assetInstanceService;

    @Autowired
    public UserAssetInstanceServiceImpl(AssetInstanceService assetInstanceService) {
        this.assetInstanceService = assetInstanceService;
    }


    @Override
    public UserAssets getUserAssets(String email) {
        List<AssetInstance> allMyBooks = assetInstanceService.getAllAssetsInstances()
                                        .getBooks().stream().filter(book -> book.getOwner().getEmail().equals(email)).collect(Collectors.toList());

        List<AssetInstance> myBooks = allMyBooks.stream().filter(book -> book.getAssetState().canBorrow()).collect(Collectors.toList());

        List<AssetInstance> lendedBooks = allMyBooks.stream().filter(book -> !book.getAssetState().canBorrow()).collect(Collectors.toList());

        List<AssetInstance> borrowedBooks = myBooks;

        return new UserAssetsImpl(myBooks, allMyBooks, borrowedBooks);
    }
}
