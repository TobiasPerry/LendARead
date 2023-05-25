package ar.edu.itba.paw.models.assetLendingContext.implementations;

import java.time.LocalDate;

public class LendingDetailsImpl {
    private final int borrowerId;
    private final int assetInstanceId;
    private final LocalDate borrowDate;
    private final LocalDate returnDate;

    public int getBorrowerId() {
        return borrowerId;
    }


    public int getAssetInstanceId() {
        return assetInstanceId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

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

