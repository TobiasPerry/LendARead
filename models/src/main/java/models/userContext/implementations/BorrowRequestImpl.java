package models.userContext.implementations;

import models.Interfaces.BorrowRequest;
import models.Interfaces.User;
import models.assetExistanceContext.implementations.AssetInstance;

import java.time.LocalDateTime;
import java.util.Date;

public class BorrowRequestImpl implements BorrowRequest {
    private final Date dateIssued ;

    private final LocalDateTime returnDate;

    private final User userReference;

    private final AssetInstance assetInstance;

    public BorrowRequestImpl(Date dateIssued, LocalDateTime returnDate, User userReference, AssetInstance assetInstance) {
        this.dateIssued = dateIssued;
        this.returnDate = returnDate;
        this.userReference = userReference;
        this.assetInstance = assetInstance;
    }
}
