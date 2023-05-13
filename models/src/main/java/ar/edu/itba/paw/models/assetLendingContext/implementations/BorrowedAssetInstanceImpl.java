package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;

final public class BorrowedAssetInstanceImpl extends AssetInstanceImpl implements BorrowedAssetInstance {

    private final String dueDate;

    private final String borrower;
    private final int lendingId;

    public BorrowedAssetInstanceImpl(final AssetInstance assetInstance, final String dueDate, final String borrower, final int lendingId) {
        super(assetInstance.getId(), assetInstance.getBook(), assetInstance.getPhysicalCondition(), assetInstance.getOwner(), assetInstance.getLocation(), assetInstance.getImageId(), assetInstance.getAssetState(),assetInstance.getMaxDays());
        this.dueDate = dueDate;
        this.borrower = borrower;
        this.lendingId = lendingId;
    }



    @Override
    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String getBorrower() {
        return borrower;
    }

    @Override
    public int getLendingId() {
        return this.lendingId;
    }
}
