package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;

final public class BorrowedAssetInstanceImpl extends AssetInstanceImpl implements BorrowedAssetInstance {

    private final String dueDate;

    private final String borrower;
    public BorrowedAssetInstanceImpl(final AssetInstance assetInstance, final String dueDate, final String borrower) {
        super(assetInstance.getId(), assetInstance.getBook(), assetInstance.getPhysicalCondition(), assetInstance.getOwner(), assetInstance.getLocation(), assetInstance.getImageId(), assetInstance.getAssetState());
        this.dueDate = dueDate;
        this.borrower = borrower;
    }

    @Override
    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String getBorrower() {
        return borrower;
    }
}
