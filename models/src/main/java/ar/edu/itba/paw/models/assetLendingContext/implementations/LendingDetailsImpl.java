package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;

import java.time.LocalDate;

public class LendingDetailsImpl implements LendingDetails {
    private final int borrowerId;
    private final int assetInstanceId;
    private final int userId;
    private final LocalDate borrowDate;
    private final LocalDate returnDate;

    @Override
    public int getBorrowerId() {
        return borrowerId;
    }

    @Override
    public int getAssetInstanceId() {
        return assetInstanceId;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    @Override
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LendingDetailsImpl(int borrowerId, int assetInstanceId, int userId, LocalDate borrowDate, LocalDate returnDate) {
        this.borrowerId = borrowerId;
        this.assetInstanceId = assetInstanceId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}

