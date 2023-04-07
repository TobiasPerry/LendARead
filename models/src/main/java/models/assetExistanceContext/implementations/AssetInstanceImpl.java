package models.assetExistanceContext.implementations;

import models.assetExistanceContext.interfaces.Asset;
import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetExistanceContext.interfaces.Book;
import models.userContext.implementations.LocationImpl;
import models.userContext.implementations.UserImpl;
import models.userContext.interfaces.Location;
import models.userContext.interfaces.User;

public class AssetInstanceImpl implements AssetInstance {

    public AssetInstanceImpl(Book book, PhysicalCondition physicalCondition, User userReference, Location location) {
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
    }

    private Book book;

    private PhysicalCondition physicalCondition;

    private User userReference;

    private Location location;
}
