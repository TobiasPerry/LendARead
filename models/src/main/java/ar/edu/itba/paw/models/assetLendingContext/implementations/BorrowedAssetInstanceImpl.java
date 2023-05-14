package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

final public class BorrowedAssetInstanceImpl extends AssetInstanceImpl implements BorrowedAssetInstance {

    private final String dueDate;

    private final String borrower;
    private final int lendingId;

    public BorrowedAssetInstanceImpl(final AssetInstance assetInstance, final String dueDate, final String borrower, final int lendingId) {
        super(assetInstance.getId(), assetInstance.getBook(), assetInstance.getPhysicalCondition(), assetInstance.getOwner(), assetInstance.getLocation(), assetInstance.getImageId(), assetInstance.getAssetState(),assetInstance.getMaxDays());
        this.dueDate = formatDate(dueDate);
        this.borrower = borrower;
        this.lendingId = lendingId;
    }

    private static String formatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime.toLocalDate().toString();
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
