package models.assetLendingContext.implementations;

import models.assetExistanceContext.implementations.AssetInstanceImp;
import models.assetLendingContext.interfaces.BorrowRequest;
import models.userContext.interfaces.User;
import models.assetExistanceContext.implementations.AssetInstance;

import java.time.LocalDateTime;
import java.util.Date;

public class BorrowRequestImpl implements BorrowRequest {
    private final Date dateIssued ;

    private final LocalDateTime returnDate;

    private final User userReference;

    private final AssetInstanceImp assetInstance;

    public BorrowRequestImpl(Date dateIssued, LocalDateTime returnDate, User userReference, AssetInstanceImp assetInstance) {
        this.dateIssued = dateIssued;
        this.returnDate = returnDate;
        this.userReference = userReference;
        this.assetInstance = assetInstance;
    }
}
