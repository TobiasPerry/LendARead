package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//final public class BorrowedAssetInstanceImpl extends AssetInstanceImpl {
//
//    private final String dueDate;
//    private final String borrower;
//    private final int lendingId;
//
//    private final LendingState lendingState;
//
//    public BorrowedAssetInstanceImpl(final AssetInstanceImpl assetInstance, final String dueDate, final String borrower, final int lendingId, final LendingState lendingState) {
//        super(assetInstance.getId(), assetInstance.getBook(), assetInstance.getPhysicalCondition(), assetInstance.getOwner(), assetInstance.getLocation(), assetInstance.getImage(), assetInstance.getAssetState(),assetInstance.getMaxDays());
//        this.dueDate = formatDate(dueDate);
//        this.borrower = borrower;
//        this.lendingId = lendingId;
//        this.lendingState = lendingState;
//    }
//
//    private static String formatDate(String date) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
//        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
//        return dateTime.toLocalDate().toString();
//    }
//
//
//
//    public String getDueDate() {
//        return dueDate;
//    }
//
//
//    public String getBorrower() {
//        return borrower;
//    }
//
//
//    public int getLendingId() {
//        return this.lendingId;
//    }
//
//
//    public LendingState getLendingState() {
//        return this.lendingState;
//    }
//
//    @Override
//    public boolean getIsBorrowedInstance() {
//        return true;
//    }
//}
