package ar.edu.itba.paw.models.assetLendingContext.implementations;

import javax.xml.stream.Location;
import java.util.List;

public class LendingHistoryImpl {
    private final List<Location> locationsList;

    public LendingHistoryImpl(List<Location> locationsList) {
        this.locationsList = locationsList;
    }
}
