package ar.edu.itba.paw.models.assetLendingContext.interfaces;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;

import java.time.LocalDate;

public interface Lending {

    Long getId();

    void setId(Long id);

    Integer getAssetInstanceId();

    void setAssetInstanceId(Integer assetInstanceId);

    Integer getBorrowerId();

    void setBorrowerId(Integer borrowerId);

    LocalDate getLendDate();

    void setLendDate(LocalDate lendDate);

    LocalDate getDevolutionDate();

    void setDevolutionDate(LocalDate devolutionDate);

    LendingState getActive();

    void setActive(LendingState active);
}
