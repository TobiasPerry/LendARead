package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;

import java.time.LocalDate;

public class LendingDetailsImpl implements LendingDetails {
    private final int borrowerId;
    private final int assetInstanceId;
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
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    @Override
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LendingDetailsImpl(int borrowerId, int assetInstanceId, LocalDate borrowDate, LocalDate returnDate) {
        this.borrowerId = borrowerId;
        this.assetInstanceId = assetInstanceId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}

