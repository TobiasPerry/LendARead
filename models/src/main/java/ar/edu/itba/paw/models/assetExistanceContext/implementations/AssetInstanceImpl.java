package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;

public class AssetInstanceImpl implements AssetInstance {

    public AssetInstanceImpl(int id, BookImpl book, PhysicalCondition physicalCondition, UserImpl userReference, LocationImpl location, int imageId, AssetState as, int maxDaysLending) {
        this.id = id;
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
        this.imageId = imageId;
        this.assetState = as;
        this.maxLendingDays = maxDaysLending;
    }

    @Override
    public String toString() {
        return "AssetInstanceImpl{" +
                "book=" + book +
                ", physicalCondition=" + physicalCondition +
                ", userReference=" + userReference +
                ", location=" + location +
                '}';
    }

    private final BookImpl book;

    private final int id;

    private final int maxLendingDays;
    private final PhysicalCondition physicalCondition;

    private final AssetState assetState;


    private final UserImpl userReference;

    private final LocationImpl location;

    private final int imageId;
    @Override
    public BookImpl getBook() {
        return book;
    }

    @Override
    public UserImpl getOwner() {
        return userReference;
    }

    @Override
    public LocationImpl getLocation() {
        return location;
    }
    @Override
    public AssetState getAssetState() {
        return assetState;
    }

    @Override
    public int getMaxDays() {
        return this.maxLendingDays;
    }

    @Override
    public int getImageId() {
        return imageId;
    }

    @Override
    public boolean getIsBorrowedInstance() {
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public PhysicalCondition getPhysicalCondition() {
        return physicalCondition;
    }
}
