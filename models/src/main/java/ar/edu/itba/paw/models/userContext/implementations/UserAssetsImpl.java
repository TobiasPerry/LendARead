package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.List;


public final class UserAssetsImpl implements UserAssets {

    private final  List<BorrowedAssetInstance> lendedBooks;
    private final   List<BorrowedAssetInstance> borrowedBooks;
    private  final   List<AssetInstance> myBooks;

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
