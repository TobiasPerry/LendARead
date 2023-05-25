package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import java.time.LocalDateTime;
import java.util.Date;

public class BorrowRequestImpl {
    private final Date dateIssued ;

    private final LocalDateTime returnDate;

    private final UserImpl userReference;

    private final AssetInstanceImpl assetInstance;

    public BorrowRequestImpl(Date dateIssued, LocalDateTime returnDate, UserImpl userReference, AssetInstanceImpl assetInstance) {
        this.dateIssued = dateIssued;
        this.returnDate = returnDate;
        this.userReference = userReference;
        this.assetInstance = assetInstance;
    }
}
