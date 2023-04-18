package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.time.LocalDate;
import java.util.Date;

public interface AssetAvailabilityService {

    boolean borrowAsset(int assetId, User borrower, LocalDate devolutionDate);

}