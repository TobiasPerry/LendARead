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
    public UserAssets filter(String table, String attribuite) {
        if(table.equals("lended_books") && attribuite.equals("pending")) {
            this.lendedBooks = this.lendedBooks.stream().filter(book -> book.getAssetState().isPending()).collect(Collectors.toList());
        }
        if(table.equals("lended_books") && attribuite.equals("delayed")) {
            this.lendedBooks = this.lendedBooks.stream().filter(book -> book.getAssetState().isDelayed()).collect(Collectors.toList());
        }
        if(table.equals("lended_books") && attribuite.equals("confirmed")) {
            this.lendedBooks = this.lendedBooks.stream().filter(book -> book.getAssetState().isBorrowed()).collect(Collectors.toList());
        }
        return this;
    }

    @Override
    public UserAssets sort(String table, String attribuite, String direction) {
        if(table.equals("lended_books") && attribuite.equals("book_name")) {
            this.lendedBooks = this.lendedBooks.stream().sorted(Comparator.comparing(a -> a.getBook().getName())).collect(Collectors.toList());
        }
        if(table.equals("lended_books") && attribuite.equals("borrower_name")) {
            this.lendedBooks = this.lendedBooks.stream().sorted(Comparator.comparing(BorrowedAssetInstance::getBorrower)).collect(Collectors.toList());
        }
        if(table.equals("lended_books") && attribuite.equals("expected_retrieval_date")) {
            this.lendedBooks = this.lendedBooks.stream().sorted(Comparator.comparing(BorrowedAssetInstance::getDueDate)).collect(Collectors.toList());
        }
        return this;
    }


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
