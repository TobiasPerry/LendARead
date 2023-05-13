package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public final class UserAssetsImpl implements UserAssets {

    private   List<BorrowedAssetInstance> lendedBooks;
    private    List<BorrowedAssetInstance> borrowedBooks;
    private  List<AssetInstance> myBooks;

    @Override
    public List<BorrowedAssetInstance> getLendedBooks() {
        return lendedBooks;
    }

    @Override
    public List<BorrowedAssetInstance> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public List<AssetInstance> getMyBooks() {
        return myBooks;
    }

    public UserAssetsImpl(List<BorrowedAssetInstance> lendedBooks, List<BorrowedAssetInstance> borrowedBooks, List<AssetInstance> myBooks) {
        this.lendedBooks = lendedBooks;
        this.borrowedBooks = borrowedBooks;
        this.myBooks = myBooks;
    }
}
