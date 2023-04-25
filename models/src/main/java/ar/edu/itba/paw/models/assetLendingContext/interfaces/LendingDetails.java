package ar.edu.itba.paw.models.assetLendingContext.interfaces;


import java.time.LocalDate;

public interface LendingDetails {
     int getBorrowerId();
     int getAssetInstanceId();
     LocalDate getBorrowDate();
     LocalDate getReturnDate();
}

