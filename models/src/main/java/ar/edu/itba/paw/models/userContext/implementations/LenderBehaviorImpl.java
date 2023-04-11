package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.userContext.interfaces.LenderBehavior;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingHistoryImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Locations;

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
