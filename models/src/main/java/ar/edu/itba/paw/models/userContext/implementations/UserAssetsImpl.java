package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.List;


public final class UserAssetsImpl implements UserAssets {

    private final  List<AssetInstance> lendedBooks;
    private final   List<AssetInstance> borrowedBooks;
    private  final   List<AssetInstance> myBooks;

    @Override
    public List<AssetInstance> getLendedBooks() {
        return lendedBooks;
    }

    @Override
    public List<AssetInstance> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public List<AssetInstance> getMyBooks() {
        return myBooks;
    }

    public UserAssetsImpl(List<AssetInstance> lendedBooks, List<AssetInstance> borrowedBooks, List<AssetInstance> myBooks) {
        this.lendedBooks = lendedBooks;
        this.borrowedBooks = borrowedBooks;
        this.myBooks = myBooks;
    }
}
