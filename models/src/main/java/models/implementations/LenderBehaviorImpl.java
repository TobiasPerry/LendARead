package models.implementations;

import models.Interfaces.LenderBehavior;

import javax.xml.stream.Location;
import java.util.List;

public class LenderBehaviorImpl implements LenderBehavior {

    private final List<Location> locationsList;

    private final LendingHistory lendingHistory;

    public LenderBehaviorImpl(List<Location> locationsList, LendingHistory lendingHistory) {
        this.locationsList = locationsList;
        this.lendingHistory = lendingHistory;
    }
}
