package models.userContext.implementations;

import models.userContext.interfaces.LenderBehavior;
import models.assetLendingContext.implementations.LendingHistoryImpl;
import models.userContext.interfaces.Locations;

import javax.xml.stream.Location;
import java.util.List;

public class LenderBehaviorImpl implements LenderBehavior {

    private final LendingHistoryImpl lendingHistory;

    private final Locations locations;

    public LenderBehaviorImpl(List<Location> locationsList, LendingHistoryImpl lendingHistory, Locations locations) {
        this.locations = locations;
        this.lendingHistory = lendingHistory;
    }
}
