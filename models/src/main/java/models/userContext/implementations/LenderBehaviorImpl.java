package models.userContext.implementations;

import models.userContext.interfaces.LenderBehavior;
import models.assetLendingContext.implementations.LendingHistoryImpl;

import javax.xml.stream.Location;
import java.util.List;

public class LenderBehaviorImpl implements LenderBehavior {

    private final List<Location> locationsList;

    private final LendingHistoryImpl lendingHistory;

    public LenderBehaviorImpl(List<Location> locationsList, LendingHistoryImpl lendingHistory) {
        this.locationsList = locationsList;
        this.lendingHistory = lendingHistory;
    }
}
